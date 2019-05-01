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
import nursing_home.pojos.*;
import sample.db.graphics.ImageWindow;
//TODO
//ARREGLAR TABLAS TRYCATCH
//MENU ACTIVITY. RELACION CON WORKER Y RESIDENT.
//WORKER Y RESIDENT RELACIÓN MUTUA. DISEÑO EN MAIN. ¿FUNCIONA?
//ON UPDATE/CASCADE. ERROR EN CREATE. ¿COMO AFECTA CUANDO EDITO/BORRO)
//ENcapsular informacion basica de todos: mostrar solo el tostring partial
//Mostrar cuantas personas hay en las habitaciones antes de introducir un resident a una?
//Insertar treatment: ¿Puede haber un treatment sin drug y dosage?
//Get Treatment/select treatment
//Update treatment

public class Ui {
	public static SQLManager sqlm = new SQLManager();
	public static JPAManager em = new JPAManager();
	static BufferedReader consola = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws NumberFormatException, IOException {
		em.connect();
		sqlm.connect();

		// TODO Solve this
		boolean exist = true;//sqlm.Check_tables_exist();
		if (exist == true) {
			sqlm.create();
		}

		Integer option = 0;
		do {
			System.out.println("Select what do you want to manage: \n1.Workers. \n2.Residents. \n3.Rooms. \n4.Activities. \n5.Drugs. \n6.Treatments. \n7.Exit");
			option = Integer.parseInt(consola.readLine());
			switch (option) {
			case 1:
				worker();
				break;
			case 2:
				resident();
				break;
			case 3:
				room();
			case 4:
				activity();
				break;
			case 5:
				drug();
				break;
			case 6:
				treatment();
				break;
			case 7:
				break;
				
			}
		} while (option != 7);
		System.out.println("Exit.");
		sqlm.disconnect();
		em.disconnect();
	}
/////////////////////////////////////WORKER MENU///////////////////////////////////

	public static void worker() throws IOException {
		int option = 0;
		do {
			System.out.println("Introduce the number:");

			System.out.println("1.New worker.\n" + "2.Basic info.\n" + "3.Details of one worker.\n" + "4.Update.\n"
					+ "5.Delete.\n" + "6.Assign a resident to a worker.\n"+"7.Return to the main menu.");
			option = Integer.parseInt(consola.readLine());
			switch (option) {

			case 1:
				newWorker();
				break;

			case 2:
				System.out.println("Showing the workers.");
				System.out.println(sqlm.selectWorkers());
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
		} while (option != 7);

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
		List<Resident> listR = sqlm.selectResidentsFromWorker(id);
		for (Resident r : listR) {
			System.out.println(r.toStringpartial());
		}
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
		} while (option != 7);
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

		List<Room> list = em.selectRooms();//TODO ¿Mostrar cuantas personas hay en las habitaciones?
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

	int option = 0;
	do {
		System.out.println("Introduce the number");

		System.out.println("1.Create new room.\n" + "2.Info about each room.\n" + "3.Details of one room.\n" + "4.Update room.\n"
				+ "5.Delete room.\n" + "6.Return to the main menu.");
		option = Integer.parseInt(consola.readLine());
		switch (option) {

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
	} while (option != 6);

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

//////////////////////////////////////ACTIVITY MENU///////////////////////////////////////////////////
public static void activity() throws IOException {
	int option = 0;
	do {
		System.out.println("Introduce the number:");

		System.out.println("1.New activity.\n" + "2.General information about all activities.\n" + "3.Details of one activity.\n"+ "4.Update activity.\n" + "5.Delete activity.\n"
				+ "6.Return to menu.\n");//Activity with worker and resident
		option = Integer.parseInt(consola.readLine());
		switch (option) {

		case 1:
			newActivity();
			break;

		case 2:
			infoActivity();
			break;

		case 3:
			activityDetails(); 
			break;

		case 4:
			updateActivity();
			break;

		case 5:
			deleteActivity();
			break;
		case 6:
			System.out.println("Going back to the menu.");
			break;
		default:
			break;
		}
	} while (option != 6);

}
public static void newActivity() throws IOException {

	System.out.println("Introduce the name of the activity.");
	String name = consola.readLine();

	System.out.println("Introduce the days of the week when the activity takes place.");
	String days = consola.readLine();
	
	System.out.println("Introduce the hours when the activity takes place.");
	String hours = consola.readLine();
	
	System.out.println("Introduce where the activity takes place.");
	String location = consola.readLine();

	Activity a = new Activity(name,hours,days,location);
	sqlm.insertActivity(a);
	System.out.println("You have created succesfully the activity "+name+".\n");
	
}
public static void infoActivity() throws IOException {
	
	System.out.println("Showing the activities.");
	List<Activity> list = sqlm.selectActivities();
	for (Activity a: list) {
		System.out.println(a.toStringpartial());
	}
}
public static void activityDetails() throws IOException {
	
	List<Activity> list = sqlm.selectActivities();
	for (Activity a: list) {
		System.out.println(a.toStringpartial());
	}
	System.out.println("Type the id of the activity to see in detail.");
	Integer id = Integer.parseInt(consola.readLine());
	Activity a = sqlm.getActivity(id);
	System.out.println(a);
	
}
public static void updateActivity() throws IOException {
	

	System.out.println(sqlm.selectActivities());
	System.out.println("Choose an activity, type its ID: ");
	Integer id = Integer.parseInt(consola.readLine());
	String answer;
	Activity a = sqlm.getActivity(id); 
	System.out.println("Do you want to change the name of the activity?");
	System.out.println("Y/N");
	answer = consola.readLine();
	if (answer.equalsIgnoreCase("Y")) {
		System.out.print("Type the new activity name: ");
		a.setName(consola.readLine());
	}
	
	System.out.println("Do you want to change the hours when the activity takes place?");
	System.out.println("Y/N");
	answer = consola.readLine();
	if (answer.equalsIgnoreCase("Y")) {
		System.out.print("Type the new hours: ");
		a.setHours(consola.readLine());
	}

	System.out.println("Do you want to change the days when the activity takes place?");
	System.out.println("Y/N");
	answer = consola.readLine();
	if (answer.equalsIgnoreCase("Y")) {
		System.out.print("Type the new days: ");
		a.setDays(((consola.readLine())));
	}
sqlm.updateActivity(a);

System.out.println("Activity updated:\n"+a);
	
}
public static void deleteActivity() throws IOException {
	
	List<Activity> list = sqlm.selectActivities();
	for (Activity a : list) {
		System.out.println(a.toStringpartial());
	}
	System.out.println("Choose an activity to delete, type its ID: ");
	Integer id = Integer.parseInt(consola.readLine());
	sqlm.deleteActivity(id);
	System.out.println("Deletion completed.");
	
}


/////////////////////////////////////DRUG MENU///////////////////////////////////

public static void drug() throws IOException {
	int option = 0;
	do {
		System.out.println("Introduce the number:");

		System.out.println("1.New drug.\n" + "2.List of drugs.\n" + "3.Delete.\n" + "4.Return to the main menu.\n");
		option = Integer.parseInt(consola.readLine());
		switch (option) {

		case 1:
			newDrug();
			break;

		case 2:
			System.out.println("Showing the drugs.");
			System.out.println(sqlm.selectDrugs());
			break;

		case 3:
			deleteDrug();
			break;

		case 4:
			System.out.println("Going back to the menu.");
			break;
		default:
			break;
		}
	} while (option != 4);

}

public static void newDrug() throws IOException {

	System.out.println("Introduce the name of the drug.");
	String name = consola.readLine();
	Drug d = new Drug(name);
	sqlm.insertDrug(d);
	System.out.println("You have created succesfully the drug "+name+".\n");
	
}

public static void deleteDrug() throws IOException {
	
	List<Drug> list = sqlm.selectDrugs();
	for (Drug d: list) {
		System.out.println(d);
	}
	System.out.println("Choose an activity to delete, type its ID: ");
	Integer id = Integer.parseInt(consola.readLine());
	sqlm.deleteActivity(id);
	System.out.println("Deletion completed.");
	
}
/////////////////////////////////////TREATMENT MENU///////////////////////////////////

public static void treatment() throws IOException {
	int option = 0;
	do {
		System.out.println("Introduce the number:");

		System.out.println("1.New treatment.\n" + "2.List all treatments.\n" + "3.Details of one treatment.\n" + "4.Update treatment.\n"
				+ "5.Delete treatment.\n" + "6.Assign a drug to a treatment.\n"+"7.Return to the main menu.");//TODO ¿Menu extra?
		option = Integer.parseInt(consola.readLine());
		switch (option) {

		case 1:
			newTreatment();
			break;

		case 2:
			infoTreatment();
			break;

		case 3:
			detailsTreatment();
			break;

		case 4:
			updateTreatment();
			break;

		case 5:
			deleteTreatment();
			break;
		case 6:
			//addResident2worker();
			break;
		case 7:
			System.out.println("Going back to the menu.");
			break;
		default:
			break;
		}
	} while (option != 7);

}
public static void newTreatment() throws IOException {

	System.out.println("Introduce the name of the treatment.");
	String name = consola.readLine();

	System.out.println("Introduce when the treatment starts(''yyyy-mm-dd'')");

	String dateini = consola.readLine();
	Date start_date = transform_date(dateini);

	System.out.println("Introduce when the treatment ends(''yyyy-mm-dd'')");

	String dateend = consola.readLine();
	Date end_date = transform_date(dateend);

	System.out.println("Now, assign the treatment into a resident.");
	System.out.println("Residents:");

	List<Resident> list = sqlm.selectResidents(); 
	for (Resident r : list) {
		System.out.println(r);
	}
	System.out.println("Type the id of the resident you want to assign the treatment.\n");
	Integer id = Integer.parseInt(consola.readLine());
	Resident r = sqlm.getResident(id);
	Treatment t = new Treatment(name,start_date,end_date,r);
	System.out.println("Type the id of the drug you want to assign to the treatment.");
	System.out.println(sqlm.selectDrugs());
	Integer id_drug = Integer.parseInt(consola.readLine());
	System.out.println("Type the dosage of that drug.");
	String dosage=consola.readLine();
	Drug d=sqlm.getDrug(id_drug);//TODO
	sqlm.insertTreatment(t, d, dosage);
	System.out.println("Treatment succesfully created.\n");
	
}
public static void infoTreatment() throws IOException {
	
	System.out.println("Showing the treatments.");
	List<Treatment> list = sqlm.selectTreatments;
	for (Treatment t: list) {
		System.out.println(t.toStringpartial());
	}
}
public static void detailsTreatment() throws IOException{
	
	List<Treatment> list = sqlm.selectTreatments;
	for (Treatment t: list) {
		System.out.println(t);
	}
	System.out.println("Type the id of the treatment to see in detail.");
	Integer id = Integer.parseInt(consola.readLine());
	Treatment t= sqlm.getTreatment(id);
	System.out.println(t);
	//A parte, mostrar la dosis y el drug.getname del treatment
	
	
}

public static void updateTreatment() throws IOException {
	

	System.out.println(sqlm.selectTreatments());//A parte, mostrar la dosis y el drug.getname del treatment

	System.out.println("Choose a treatment, type its ID: ");
	Integer id = Integer.parseInt(consola.readLine());
	String answer,dosage;
	Treatment t= sqlm.getTreatment();
	System.out.println("Do you want to change the name of the Treatment?");
	System.out.println("Y/N");
	answer = consola.readLine();
	if (answer.equalsIgnoreCase("Y")) {
		System.out.print("Type the new treatment's name: ");
		t.setName(consola.readLine());
	}
	System.out.println("Do you want to change when the treatment ends?");
	System.out.println("Y/N");
	answer = consola.readLine();
	if (answer.equalsIgnoreCase("Y")) {
		System.out.print("Type the new treatment's end date: ");
		String dateend = consola.readLine();
		Date end_date = transform_date(dateend);
		t.setFinal_date(end_date);
	}
	System.out.println("Do you want to change the dosage of the Treatment?");
	System.out.println("Y/N");
	answer = consola.readLine();
	if (answer.equalsIgnoreCase("Y")) {
		System.out.print("Type the new treatment's dosage: ");
		dosage= consola.readLine();//TODO si no quiere cambiar el dosage, que dosage tenga el valor anterior
	}
	

	sqlm.updateTreatment(t,dosage);
	System.out.println("Treatment updated:\n"+t);

	
}
public static void deleteTreatment() throws IOException {
	
	List<Treatment> list = sqlm.selectTreatments();
	for (Treatment t: list) {
		System.out.println(t);
	}
	System.out.println("Choose a treatment to delete, type its ID: ");
	Integer id = Integer.parseInt(consola.readLine());
	sqlm.deleteTreatment(id);
	System.out.println("Deletion completed.");
	
}

	
	public static Date transform_date(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate final_date = LocalDate.parse(date, formatter);
		return Date.valueOf(final_date);
	}
}
