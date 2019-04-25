package nursing_home.ui;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import nursing_home.db.jdbc.SQLManager;
import nursing_home.db.jpa.JPAManager;
import nursing_home.pojos.Resident;
import nursing_home.pojos.Room;
import nursing_home.pojos.Worker;
import sample.db.graphics.ImageWindow;

public class Ui {
	public static SQLManager sqlm = new SQLManager();
	public static JPAManager em = new JPAManager();
	static BufferedReader consola = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws NumberFormatException, IOException {
		em.connect();
		sqlm.connect();

		// TODO Solve this
		boolean exist = true;//sqlm.Check_tables_exist();
		if (exist == false) {
			sqlm.create();
		}

		Integer opcion1 = 0;
		do {
			System.out.println("Select what do you want to manage: \n1.Workers. \n2.Residents. \n3.Rooms. \n4.Exit.");
			opcion1 = Integer.parseInt(consola.readLine());
			switch (opcion1) {
			case 1:
				worker();
				break;
			case 2:
				resident();
				break;
			case 3:
				room();
			case 4:

				break;
			}
		} while (opcion1 != 4);
		System.out.println("Exit.");
		sqlm.disconnect();
		em.disconnect();
	}
/////////////////////////////////////WORKER MENU///////////////////////////////////

	public static void worker() throws IOException {
		int opcionw = 0;
		do {
			System.out.println("Introduce the number:");

			System.out.println("1.New worker.\n" + "2.Basic info.\n" + "3.Details of one worker.\n" + "4.Update.\n"
					+ "5.Delete.\n" + "6.Assign a resident to a worker.\n"+"7.Return to the main menu.");
			opcionw = Integer.parseInt(consola.readLine());
			switch (opcionw) {

			case 1:
				newWorker();
				break;

			case 2:
				System.out.println("Showing the workers.");
				System.out.println(sqlm.selectWorkers());
				System.out.println("Search finished.");
				break;

			case 3:
				workerDetails(); //TODO show the residents that the worker has in charge
				break;

			case 4:
				updateWorker();
				break;

			case 5:
				deleteWorker();
				break;
			case 6:
				addResident2worker();
				break;
			case 7:
				System.out.println("Going back to the menu.");
				break;
			default:
				break;
			}
		} while (opcionw != 7);

	}

	public static void newWorker() throws IOException {

		System.out.println("Introduce the name");
		String name = consola.readLine();

		System.out.println("Introduce the gender");
		String gender = consola.readLine();

		System.out.println("Introduce the job");
		String job = consola.readLine();

		System.out.println("Introduce the birth date(''yyyy-mm-dd'')");

		String birth_date = consola.readLine();
		Date dob = transform_date(birth_date);

		System.out.println("Introduce the hire date(''yyyy-mm-dd'')");

		String hire_date = consola.readLine();
		Date final_date = transform_date(hire_date);

		System.out.println("Introduce the salary");

		Double salary = Double.parseDouble(consola.readLine());

		System.out.println("Introduce the name of the photo as it appears in the folder");
		String fileName = consola.readLine();
		;

		File photo = new File("./photos/" + fileName);
		InputStream streamBlob = new FileInputStream(photo);

		byte[] bytesBlob = new byte[streamBlob.available()];
		streamBlob.read(bytesBlob);
		streamBlob.close();
		Worker w = new Worker(name, gender, job, final_date, dob, salary, bytesBlob);
		sqlm.insertWorker(w);
	}

	public static void workerDetails() throws IOException {

		List<Worker> list = sqlm.selectWorkers();
		for (Worker worker : list) {
			System.out.println(worker.toStringpartial());
		}
		System.out.println("Type the id of the worker to see in detail.");
		Integer id = Integer.parseInt(consola.readLine());
		Worker w = sqlm.getWorker(id);
		System.out.println(w);// It prints all the info of the person
		// Now, we show the photo
		if (w.getPhoto() != null) {
			ByteArrayInputStream blobIn = new ByteArrayInputStream(w.getPhoto());
			System.out.println("test");
			ImageWindow window = new ImageWindow();
			window.showBlob(blobIn);
		}

	}

	public static void deleteWorker() throws IOException {

		List<Worker> list = sqlm.selectWorkers();
		for (Worker worker : list) {
			System.out.println(worker.toStringpartial());
		}
		System.out.println("Choose a worker to delete, type its ID: ");
		Integer id = Integer.parseInt(consola.readLine());
		sqlm.deleteWorker(id);
		System.out.println("Deletion completed.");

	}

	public static void updateWorker() throws IOException {

		System.out.println(sqlm.selectWorkers());
		System.out.println("Choose a worker, type its ID: ");
		Integer id = Integer.parseInt(consola.readLine());
		String answer;
		Worker w = sqlm.getWorker(id);
		System.out.println("Do you want to change the name?");
		System.out.println("Y/N");
		answer = consola.readLine();
		if (answer.equalsIgnoreCase("Y")) {
			System.out.print("Type the new worker's name: ");
			w.setName(consola.readLine());
		}
		System.out.println("Do you want to change the job?");
		System.out.println("Y/N");
		answer = consola.readLine();
		if (answer.equalsIgnoreCase("Y")) {
			System.out.print("Type the new worker's job: ");
			w.setJob(consola.readLine());
		}
		System.out.println("Do you want to change the salary?");
		System.out.println("Y/N");
		answer = consola.readLine();
		if (answer.equalsIgnoreCase("Y")) {
			System.out.print("Type the new worker's salary: ");
			w.setSalary(Double.parseDouble(consola.readLine()));
		}
		sqlm.updateWorker(w);
		System.out.println("Worker updated:\n"+w);

	}
	public static void addResident2worker() throws IOException{
	
	System.out.println("Introduce the ID of the worker in which you want to assign a resident.");
	List<Worker> listw=sqlm.selectWorkers();
	for (Worker w : listw) {
		System.out.println(w.toStringpartial());
	}
	Integer w_id=Integer.parseInt(consola.readLine());
	
	System.out.println("Now, introduce the ID of the resident you want to assign to the resident you chose");
	List<Resident> listr=sqlm.selectResidents();
	for (Resident r : listr) {
		System.out.println(r.toStringpartial());
	}
	Integer r_id=Integer.parseInt(consola.readLine());
	
	sqlm.connectResidentWorker(w_id ,r_id);
	System.out.println("Assignment done.\n");
	}


/////////////////////////////////////RESIDENT MENU///////////////////////////////////
	public static void resident() throws IOException {
		int option = 0;
		do {
			System.out.println("Introduce the number");

			System.out.println("1.New resident.\n" + "2.Basic info.\n" + "3.See details of 1 resident.\n"
					+ "4.Update.\n" + "5.Delete.\n" + "6.Assign a worker to a resident.\n"+"7.Return to the main menu.");
			option = Integer.parseInt(consola.readLine());
			switch (option) {

			case 1:
				newResident();
				break;

			case 2:
				System.out.println("Showing the residents.");
				System.out.println(sqlm.selectResidents());
				break;

			case 3:
				detailsResident();//TODO show the workers in charge of him/her
				break;

			case 4:
				updateResident();
				break;

			case 5:
				deleteResident();
				break;
			case 6:
				addWorker2resident();
				break;
			case 7:
				System.out.println("Going back to the menu.");
				break;
			default:
				break;
			}
		} while (option != 6);
	}
	public static void newResident() throws IOException {

		System.out.println("Introduce the name.");
		String name = consola.readLine();

		System.out.println("Introduce the gender.");
		String gender = consola.readLine();

		System.out.println("Introduce the birth date(''yyyy-mm-dd'')");

		String birth_date = consola.readLine();
		Date dob = transform_date(birth_date);

		System.out.println("Introduce the contact telephone.");
		Integer tel = Integer.parseInt(consola.readLine());

		System.out.println("Introduce the grade of dependency.");
		String grade = consola.readLine();

		System.out.println("Introduce the date of check-in(''yyyy-mm-dd'')");

		String checkin = consola.readLine();
		Date date_check = transform_date(checkin);

		System.out.println("Introduce any extra notes or details about the resident.");
		String notes = consola.readLine();

		System.out.println("Introduce the name of the photo as it appears in the folder.");
		String fileName = consola.readLine();

		File photo = new File("./photos/" + fileName);
		InputStream streamBlob = new FileInputStream(photo);

		byte[] bytesBlob = new byte[streamBlob.available()];
		streamBlob.read(bytesBlob);
		streamBlob.close();

		System.out.println("Now, assign the patient into a room.");
		System.out.println("Rooms available:");

		List<Room> list = em.selectRooms();// ¿Mostrar cuantas personas hay en las habitaciones?
		for (Room r : list) {
			System.out.println(r);
		}
		System.out.println("Type the id of the room you want to assign to the resident.");
		Integer id = Integer.parseInt(consola.readLine());
		Room r = em.getRoom(id);
		Resident r1 = new Resident(name, gender, dob, tel, grade, date_check, bytesBlob, notes, r);
		em.insertResident(r1);
		System.out.println("Resident succesfully created.\n");
		
	}

	public static void detailsResident() throws IOException{
		
		List<Resident> list = em.selectResidents();
		for (Resident r : list) {
			System.out.println(r.toStringpartial());
		}
		System.out.println("Type the id of the resident to see in detail.");
		Integer id = Integer.parseInt(consola.readLine());
		Resident r = em.getResident(id);
		System.out.println(r);// It prints all the info of the person
		// Now, we show the photo
		if (r.getPhoto() != null) {
			ByteArrayInputStream blobIn = new ByteArrayInputStream(r.getPhoto());
			ImageWindow window = new ImageWindow();
			window.showBlob(blobIn);
		}
		
	}
	
	public static void deleteResident() throws IOException {
		
		List<Resident> list = em.selectResidents();
		for (Resident r : list) {
			System.out.println(r.toStringpartial());
		}
		System.out.println("Choose a resident to delete, type its ID: ");
		Integer id = Integer.parseInt(consola.readLine());
		em.deleteResident(id);
		System.out.println("Deletion completed.");
		
		
	}
	public static void updateResident() throws IOException {
		

		System.out.println(em.selectResidents());
		System.out.println("Choose a resident, type its ID: ");
		Integer id = Integer.parseInt(consola.readLine());
		String answer;
		Resident r = em.getResident(id);
		System.out.println("Do you want to change the name?");
		System.out.println("Y/N");
		answer = consola.readLine();
		if (answer.equalsIgnoreCase("Y")) {
			System.out.print("Type the new resident's name: ");
			r.setName(consola.readLine());
		}
		System.out.println("Do you want to change the contact telephone?");
		System.out.println("Y/N");
		answer = consola.readLine();
		if (answer.equalsIgnoreCase("Y")) {
			System.out.print("Type the new resident's contact teleph: ");
			r.setTeleph(Integer.parseInt((consola.readLine())));
		}
		System.out.println("Do you want to change the grade of dependency?");
		System.out.println("Y/N");
		answer = consola.readLine();
		if (answer.equalsIgnoreCase("Y")) {
			System.out.print("Type the new residents's grade of dependency: ");
			r.setDep_grade((consola.readLine()));
		}

		System.out.println("Do you want to change the notes?");
		System.out.println("Y/N");
		answer = consola.readLine();
		if (answer.equalsIgnoreCase("Y")) {
			System.out.print("Type the new residents's notes: ");
			r.setNotes(((consola.readLine())));
		}

		System.out.println("Do you want to change the resident's room?");
		System.out.println("Y/N");
		answer = consola.readLine();
		if (answer.equalsIgnoreCase("Y")) {
			System.out.print("Type the new residents's room (id): ");
			int room_id = Integer.parseInt(consola.readLine());
			Room ro = em.getRoom(room_id);
			r.setRoom(ro);
		}

		em.updateResident(r);
		System.out.println("Resident updated:\n"+r);

		
	}
	
	public static void addWorker2resident() throws IOException{
		
	System.out.println("Introduce the ID of the resident you want to assign a worker.");
	List<Resident> listr=sqlm.selectResidents();
	for (Resident r : listr) {
		System.out.println(r.toStringpartial());
	}
	Integer r_id=Integer.parseInt(consola.readLine());
	System.out.println("Now, introduce the ID of the worker you want to assign the resident you chose");

	List<Worker> listw=sqlm.selectWorkers();
	for (Worker w : listw) {
		System.out.println(w.toStringpartial());
	}
	Integer w_id=Integer.parseInt(consola.readLine());
	
	sqlm.connectResidentWorker(w_id ,r_id);
	System.out.println("Assignment done.\n");
	}
	
/////////////////////////////////////ROOM MENU///////////////////////////////////
public static void room() throws IOException{

	int opcionw = 0;
	do {
		System.out.println("Introduce the number");

		System.out.println("1.Create new room.\n" + "2.Info about each room.\n" + "3.Details of one room.\n" + "4.Update room.\n"
				+ "5.Delete room.\n" + "6.Return to the main menu.");
		opcionw = Integer.parseInt(consola.readLine());
		switch (opcionw) {

		case 1:
			newRoom();
			break;

		case 2:
			System.out.println("Showing the information of the rooms.");
			System.out.println(sqlm.selectRooms());
			break;

		case 3:
			roomDetails();
			break;

		case 4:
			updateRoom();
			break;

		case 5:
			deleteRoom();
			break;
		case 6:
			System.out.println("Going back to the menu.\n");
			break;
		default:
			break;
		}
	} while (opcionw != 6);

}
public static void newRoom() throws IOException {

	System.out.println("Introduce the room type.");
	String roomtype = consola.readLine();

	System.out.println("Introduce the floor in which the room is located.");
	Integer floor = Integer.parseInt(consola.readLine());

	System.out.println("Introduce the gender-type of the room (Male/female/mixed).");
	String gender = consola.readLine();
	
	System.out.println("Introduce the facilities that the room has.");
	String notes = consola.readLine();
	Room room = new Room(roomtype, floor, notes, gender);
	em.insertRoom(room);//jpa
	System.out.println("You have created succesfully a room in the floor "+floor+".\n");
}
public static void roomDetails() throws IOException{
	
	List<Room> list = em.selectRooms();//jpa
	for (Room r : list) {
		System.out.println(r.toStringpartial());
	}
	System.out.println("Type the id of the room to see in detail.");
	Integer id = Integer.parseInt(consola.readLine());
	Room r = em.getRoom(id);//jpa
	System.out.println(r);// It prints all the room
	// Now, we show the the people it has inside
	System.out.println("Residents living in this room:");
	List<Resident> listr = r.getResidents();
	for (Resident re : listr) {
		System.out.println(re.toStringpartial());
	}
	
}
public static void updateRoom() throws IOException {
	

	System.out.println(em.selectRooms());
	System.out.println("Choose a room, type its ID: ");
	Integer id = Integer.parseInt(consola.readLine());
	String answer;
	Room r = em.getRoom(id);//with JPA 
	System.out.println("Do you want to change the room type?");
	System.out.println("Y/N");
	answer = consola.readLine();
	if (answer.equalsIgnoreCase("Y")) {
		System.out.print("Type the new room type: ");
		r.setRoomtype(consola.readLine());
	}
	
	System.out.println("Do you want to change the gender of the room?");
	System.out.println("Y/N");
	answer = consola.readLine();
	if (answer.equalsIgnoreCase("Y")) {
		System.out.print("Type the new gender of the room: ");
		r.setGender(consola.readLine());
	}

	System.out.println("Do you want to change the facilities that the room has?");
	System.out.println("Y/N");
	answer = consola.readLine();
	if (answer.equalsIgnoreCase("Y")) {
		System.out.print("Type the new facilities: ");
		r.setNotes(((consola.readLine())));
	}
	em.updateRoom(r);//with jpa
	System.out.println("Room updated:\n"+r);

	
}
public static void deleteRoom() throws IOException {
	
	List<Room> list = em.selectRooms();//with jpa
	for (Room r : list) {
		System.out.println(r.toStringpartial());
	}
	System.out.println("Choose a room to delete, type its ID: ");
	Integer id = Integer.parseInt(consola.readLine());
	em.deleteRoom(id);//jpa
	System.out.println("Deletion completed.");
	
	
}
	
	public static Date transform_date(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate final_date = LocalDate.parse(date, formatter);
		return Date.valueOf(final_date);
	}
}
