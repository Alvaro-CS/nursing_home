package nursing_home.ui;

import java.io.*;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import nursing_home.db.jdbc.SQLManager;
import nursing_home.db.jpa.JPAManager;
import nursing_home.db.xml.XMLManager;
import nursing_home.pojos.*;
import sample.db.graphics.ImageWindow;

public class Ui {
	public static SQLManager sqlm = new SQLManager();
	public static JPAManager em = new JPAManager();
	public static XMLManager xm = new XMLManager();

	static BufferedReader consola = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws NumberFormatException, IOException, JAXBException {

		em.connect();
		sqlm.connect();
		sqlm.create();

		Integer option = 0;
		do {
			System.out.println("~ ~ ~ ~ MAIN MENU ~ ~ ~ ~");
			System.out.println(
					"Select what do you want to manage: \n1.Workers. \n2.Residents. \n3.Rooms. \n4.Activities. \n5.Drugs. \n6.Treatments. \n7.XML. \n8.Exit");
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
				break;
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
				xml();
				break;
			case 8:
				break;

			}
		} while (option != 8);
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
					+ "5.Delete.\n" + "6.Manage relationship worker-resident.\n" + "7.Return to the main menu.");
			option = Integer.parseInt(consola.readLine());
			switch (option) {

			case 1:
				newWorker();
				break;

			case 2:
				infoWorker();
				break;

			case 3:
				workerDetails();
				break;

			case 4:
				updateWorker();
				break;

			case 5:
				deleteWorker();
				break;
			case 6:
				workerResident();
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

	public static void infoWorker() throws IOException {

		System.out.println("Showing the workers.");
		List<Worker> list = sqlm.selectWorkers();
		for (Worker w : list) {
			System.out.println(w.toStringpartial());
		}
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
		System.out.println("Residents assigned to " + w.getName() + ":");
		List<Resident> listR = sqlm.selectResidentsFromWorker(id);
		for (Resident r : listR) {
			System.out.println(r.toStringpartial());
		}
		List<Activity> list_a = sqlm.selectActivitiesFromWorker(w.getId());
		System.out.println(w.getName() + " is in charged of the following activities:");
		for (Activity a : list_a) {
			System.out.println(a.toStringpartial());
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
		System.out.println("Worker updated:\n" + w);

	}

	public static void workerResident() throws IOException {

		int option = 0;
		do {
			System.out.println("Introduce the number:");

			System.out.println("1.Assign a resident to a worker.\n"
					+ "2.Delete a relationship between a resident and a worker.\n" + "3.Return to \"Worker\".\n");
			option = Integer.parseInt(consola.readLine());
			switch (option) {

			case 1:
				addResident2worker();
				break;

			case 2:
				deleteWorkerResident();
				break;

			case 3:
				System.out.println("Going back to \"Workers\".");
				break;
			default:
				break;
			}
		} while (option != 3);

	}

	public static void addResident2worker() throws IOException {

		System.out.println("Introduce the ID of the worker in which you want to assign a resident.");
		List<Worker> listw = sqlm.selectWorkers();
		for (Worker w : listw) {
			System.out.println(w.toStringpartial());
		}
		Integer w_id = Integer.parseInt(consola.readLine());

		System.out.println("Now, introduce the ID of the resident you want to assign to the resident you chose");
		List<Resident> listr = sqlm.selectResidents();
		for (Resident r : listr) {
			System.out.println(r.toStringpartial());
		}
		Integer r_id = Integer.parseInt(consola.readLine());

		sqlm.connectResidentWorker(w_id, r_id);
		System.out.println("Assignment done.\n");
	}

	public static void deleteWorkerResident() throws IOException {

		System.out.println("Introduce the ID of the worker you want to delete a resident from.");
		List<Worker> listw = sqlm.selectWorkers();
		for (Worker w : listw) {
			System.out.println(w.toStringpartial());
		}
		Integer w_id = Integer.parseInt(consola.readLine());
		List<Resident> listr = sqlm.selectResidentsFromWorker(w_id);
		if (listr.size() == 0) {
			System.out.println("There are no residents related to that worker.");
		} else {
			System.out.println("Now, introduce the ID of the resident you want to delete from the worker.");
			for (Resident r : listr) {
				System.out.println(r.toStringpartial());
			}
			Integer r_id = Integer.parseInt(consola.readLine());

			sqlm.disconnectResidentWorker(w_id, r_id);
			System.out.println("Deletion done.\n");
		}
	}

/////////////////////////////////////RESIDENT MENU///////////////////////////////////
	public static void resident() throws IOException {
		int option = 0;
		do {
			System.out.println("Introduce the number");

			System.out.println("1.New resident.\n" + "2.Basic info.\n" + "3.See details of 1 resident.\n"
					+ "4.Update.\n" + "5.Delete.\n" + "6.Manage relationship resident-worker.\n"
					+ "7.Return to the main menu.");
			option = Integer.parseInt(consola.readLine());
			switch (option) {

			case 1:
				newResident();
				break;

			case 2:
				infoResidents();
				break;

			case 3:
				detailsResident();
				break;

			case 4:
				updateResident();
				break;

			case 5:
				deleteResident();
				break;
			case 6:
				residentWorker();
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
		
		if (sqlm.selectRooms().size() == 0) {
			System.out.println("You first need to have at least one room created to create a new resident.");
		} 
		else {
			System.out.println("Introduce the name.");
			String name = consola.readLine();

			System.out.println("Introduce the gender.");
			;
			System.out.println("Type \"M\" for male or \"F\" for female:");
			String gender = "";
			String option = consola.readLine();
			if (option.equalsIgnoreCase("M"))
				gender = "Male";
			else {
				gender = "Female";
			}
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
			Room r = roomAssign(gender);
			Resident r1 = new Resident(name, gender, dob, tel, grade, date_check, bytesBlob, notes, r);
			em.insertResident(r1);
			System.out.println("Resident succesfully created.\n");
		}
	}

	public static void infoResidents() throws IOException {

		System.out.println("Showing the residents.");
		List<Resident> list = sqlm.selectResidents();
		for (Resident r : list) {
			System.out.println(r.toStringpartial());
		}
	}

	public static void detailsResident() throws IOException {

		List<Resident> list = em.selectResidents();
		for (Resident r : list) {
			System.out.println(r.toStringpartial());
		}
		System.out.println("Type the id of the resident to see in detail.");
		Integer id = Integer.parseInt(consola.readLine());
		Resident r = em.getResident(id);
		System.out.println(r.toStringRID());// It prints all the info of the person
		System.out.println("Workers assigned to " + r.getName() + ":");
		List<Worker> workers_list = sqlm.selectWorkersFromResident(id);
		for (Worker w : workers_list) {
			System.out.println(w.toStringpartial());
		}
		List<Activity> list_a = sqlm.selectActivitiesFromResident(r.getId());
		System.out.println(r.getName() + " has sign up to these activities:");
		for (Activity a : list_a) {
			System.out.println(a.toStringpartial());
		}
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
			Room ro = roomAssign(r.getGender());
			r.setRoom(ro);
		}

		em.updateResident(r);
		System.out.println("Resident updated:\n" + r);

	}

	public static Room roomAssign(String gender) throws IOException {

		System.out.println("Rooms available:");
		List<Room> list = em.selectRooms();
		boolean valid = false;
		Room r = null;
		List<Resident> listr = null;
		do {
			for (Room rl : list) {
				System.out.println(rl);
				listr = rl.getResidents();
				System.out.println("Residents in the room:" + em.countResidentsFromRoom(rl.getId()) + ".\n");

			}

			System.out.println("Type the id of the room you want to assign to the resident.");

			Integer id = Integer.parseInt(consola.readLine());
			r = em.getRoom(id);
			if (r.getGender().equalsIgnoreCase(gender) || r.getGender().equalsIgnoreCase("Mixed")) {
				if (r.getRoomtype().equalsIgnoreCase("Single") && em.countResidentsFromRoom(r.getId()) > 0) {
					System.out.println("The room is full, it can only have 1 resident, please select another one.");
				} else {
					if (r.getRoomtype().equalsIgnoreCase("Double") && em.countResidentsFromRoom(r.getId()) > 1) {
						System.out
								.println("The room is full, it can only have 2 residents, please select another one.");
					} else {
						valid = true;
					}
				}
			} else {
				System.out.println("The gender-type of the room doesn't match to the gender of the resident.\n"
						+ "Please select a room valid for a " + gender + " person.");
			}

		} while (valid == false);
		return r;

	}

	public static void residentWorker() throws IOException {

		int option = 0;
		do {
			System.out.println("Introduce the number:");

			System.out.println("1.Assign a worker to a resident.\n"
					+ "2.Delete a relationship between a worker and a resident.\n" + "3.Return to \"Residents\".\n");
			option = Integer.parseInt(consola.readLine());
			switch (option) {

			case 1:
				addWorker2resident();
				break;

			case 2:
				deleteWorkerResident();
				break;

			case 3:
				System.out.println("Going back to \"Residents\".");
				break;
			default:
				break;
			}
		} while (option != 3);

	}

	public static void addWorker2resident() throws IOException {

		System.out.println("Introduce the ID of worker you want to assign to a resident.");
		List<Worker> listw = sqlm.selectWorkers();
		for (Worker w : listw) {
			System.out.println(w.toStringpartial());
		}

		Integer w_id = Integer.parseInt(consola.readLine());
		System.out.println("Now, introduce the ID of the resident you want to assign to the worker you chose.");

		List<Resident> listr = sqlm.selectResidents();
		for (Resident r : listr) {
			System.out.println(r.toStringpartial());
		}

		Integer r_id = Integer.parseInt(consola.readLine());

		sqlm.connectResidentWorker(w_id, r_id);
		System.out.println("Assignment done.\n");
	}

	public static void deleteResidentWorker() throws IOException {

		System.out.println("Introduce the ID of the resident you want to delete a worker from.");
		List<Resident> listr = sqlm.selectResidents();
		for (Resident r : listr) {
			System.out.println(r.toStringpartial());
		}
		Integer r_id = Integer.parseInt(consola.readLine());
		List<Worker> listw = sqlm.selectWorkersFromResident(r_id);
		if (listw.size() == 0) {
			System.out.println("There are no workers related to that resident.");
		} else {
			System.out.println("Now, introduce the ID of the worker you want to delete from the resident.");
			for (Worker w : listw) {
				System.out.println(w.toStringpartial());
			}
			Integer w_id = Integer.parseInt(consola.readLine());

			sqlm.disconnectResidentWorker(w_id, r_id);
			System.out.println("Deletion done.\n");
		}
	}

/////////////////////////////////////ROOM MENU///////////////////////////////////
	public static void room() throws IOException {

		int option = 0;
		do {
			System.out.println("Introduce the number");

			System.out.println("1.Create new room.\n" + "2.Info about each room.\n" + "3.Details of one room.\n"
					+ "4.Update room.\n" + "5.Delete room.\n" + "6.Return to the main menu.");
			option = Integer.parseInt(consola.readLine());
			switch (option) {

			case 1:
				newRoom();
				break;

			case 2:
				infoRooms();
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
		System.out.println("Type \"S\" for single room or \"D\" for double room:");
		String roomtype = "";
		String option = consola.readLine();
		if (option.equalsIgnoreCase("S"))
			roomtype = "Single";
		else {
			roomtype = "Double";
		}

		System.out.println("Introduce the floor in which the room is located.");
		Integer floor = Integer.parseInt(consola.readLine());

		System.out.println("Introduce the gender-type of the room.");
		System.out.println("Type \"M\" for male, \"F\" for female or \"X\" for mixed:");
		String gender = "";
		option = consola.readLine();
		if (option.equalsIgnoreCase("M"))
			gender = "Male";
		else if (option.equalsIgnoreCase("F")) {
			gender = "Female";
		} else {
			gender = "Mixed";
		}
		System.out.println("Introduce the facilities that the room has.");
		String notes = consola.readLine();
		Room room = new Room(roomtype, floor, notes, gender);
		em.insertRoom(room);// jpa
		System.out.println("You have created succesfully a room in the floor " + floor + ".\n");
	}

	public static void infoRooms() throws IOException {

		System.out.println("Showing the rooms.");
		List<Room> list = sqlm.selectRooms();
		for (Room r : list) {
			System.out.println(r.toStringpartial());
		}
	}

	public static void roomDetails() throws IOException {

		List<Room> list = em.selectRooms();// jpa
		for (Room r : list) {
			System.out.println(r.toStringpartial());
		}
		System.out.println("Type the id of the room to see in detail.");
		Integer id = Integer.parseInt(consola.readLine());
		Room r = em.getRoom(id);// jpa
		System.out.println(r);// It prints all the room
		// Now, we show the the people it has inside
		System.out.println("Residents living in this room:");
		List<Resident> listr = r.getResidents();
		for (Resident re : listr) {
			System.out.println(re.toStringpartial());
		}

	}

	public static void updateRoom() throws IOException {

		List<Room> list = sqlm.selectRooms();
		for (Room r : list) {
			System.out.println(r.toStringpartial());
		}
		System.out.println("Choose a room, type its ID: ");
		Integer id = Integer.parseInt(consola.readLine());
		String answer;
		Room r = em.getRoom(id);// with JPA
		System.out.println("Do you want to change the room type?");
		System.out.println("Y/N");
		answer = consola.readLine();
		if (answer.equalsIgnoreCase("Y")) {
			System.out.print("Type the new room type. ");
			System.out.println("Type \"S\" for single room or \"D\" for double room:");
			String room_type = consola.readLine();
			if (room_type.equalsIgnoreCase("S"))
				r.setRoomtype("Single");
			else {
				r.setRoomtype("Double");
			}

		}

		System.out.println("Do you want to change the gender of the room?");
		System.out.println("Y/N");
		answer = consola.readLine();
		if (answer.equalsIgnoreCase("Y")) {
			
			System.out.println("Type \"M\" for male, \"F\" for female or \"X\" for mixed:");
			String gender = "";
			String option = consola.readLine();
			if (option.equalsIgnoreCase("M"))
				gender = "Male";
			else if (option.equalsIgnoreCase("F")) {
				gender = "Female";
			} else {
				gender = "Mixed";
			}
			r.setGender(consola.readLine());
		}

		System.out.println("Do you want to change the facilities that the room has?");
		System.out.println("Y/N");
		answer = consola.readLine();
		if (answer.equalsIgnoreCase("Y")) {
			System.out.print("Type the new facilities: ");
			r.setNotes(((consola.readLine())));
		}
		em.updateRoom(r);// with jpa
		System.out.println("Room updated:\n" + r);

	}

	public static void deleteRoom() throws IOException {

		List<Room> list = em.selectRooms();// with jpa
		for (Room r : list) {
			System.out.println(r.toStringpartial());
		}
		System.out.println("Choose a room to delete, type its ID: ");
		Integer id = Integer.parseInt(consola.readLine());
		em.deleteRoom(id);// jpa
		System.out.println("Deletion completed.");

	}

//////////////////////////////////////ACTIVITY MENU///////////////////////////////////////////////////
	public static void activity() throws IOException {
		int option = 0;
		do {
			System.out.println("Introduce the number:");

			System.out.println("1.New activity.\n" + "2.General information about all activities.\n"
					+ "3.Details of one activity.\n" + "4.Update activity.\n" + "5.Delete activity.\n"
					+ "6.Activity management by workers.\n" + "7.Activity management of residents.\n"
					+ "8.Return to menu.\n");
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
				workerActivity();
				break;
			case 7:
				residentActivity();
				break;
			case 8:
				System.out.println("Going back to the menu.");
				break;
			default:
				break;
			}
		} while (option != 8);

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

		Activity a = new Activity(name, hours, days, location);
		sqlm.insertActivity(a);
		System.out.println("You have created succesfully the activity " + name + ".\n");

	}

	public static void infoActivity() throws IOException {

		System.out.println("Showing the activities.");
		List<Activity> list = sqlm.selectActivities();
		for (Activity a : list) {
			System.out.println(a.toStringpartial());
		}
		System.out.print("\n");
	}

	public static void activityDetails() throws IOException {

		List<Activity> list = sqlm.selectActivities();
		for (Activity a : list) {
			System.out.println(a.toStringpartial());
		}
		System.out.println("Type the id of the activity to see in detail.");
		Integer id = Integer.parseInt(consola.readLine());
		Activity a = sqlm.getActivity(id);
		System.out.println(a);
		List<Worker> listw = sqlm.selectWorkersFromActivity(id);
		List<Resident> listr = sqlm.selectResidentsFromActivity(id);
		for (Worker w : listw) {
			System.out.println(w.toStringpartial());
		}
		for (Resident r : listr) {
			System.out.println(r.toStringpartial());
		}

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

		System.out.println("Activity updated:\n" + a);

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

	public static void workerActivity() throws IOException {

		int option = 0;
		do {
			System.out.println("Introduce the number:");

			System.out.println("1.Assign an activity to a worker.\n"
					+ "2.Delete a relationship between an activity and a worker.\n" + "3.Return to \"Activities\".\n");
			option = Integer.parseInt(consola.readLine());
			switch (option) {

			case 1:
				addWorker2activity();
				break;

			case 2:
				deleteWorkerActivity();
				break;

			case 3:
				System.out.println("Going back to \"Activities\".");
				break;
			default:
				break;
			}
		} while (option != 3);
	}

	public static void addWorker2activity() throws IOException {

		System.out.println("Introduce the ID of worker you want to assign to an activity.");
		List<Worker> listw = sqlm.selectWorkers();
		for (Worker w : listw) {
			System.out.println(w.toStringpartial());
		}

		Integer w_id = Integer.parseInt(consola.readLine());
		System.out.println("Now, introduce the ID of the activity you want to assign to the worker you chose.");

		List<Activity> list = sqlm.selectActivities();
		for (Activity a : list) {
			System.out.println(a.toStringpartial());
		}

		Integer a_id = Integer.parseInt(consola.readLine());

		sqlm.connectActivityWorker(w_id, a_id);
		System.out.println("Assignment done.\n");
	}

	public static void deleteWorkerActivity() throws IOException {

		System.out.println("Introduce the ID of the activity you want to delete a worker from.");
		List<Activity> list = sqlm.selectActivities();
		for (Activity a : list) {
			System.out.println(a.toStringpartial());
		}
		Integer a_id = Integer.parseInt(consola.readLine());
		List<Worker> listw = sqlm.selectWorkersFromActivity(a_id);
		if (listw.size() == 0) {
			System.out.println("There are no workers in charge of that activity.");
		} else {
			System.out.println("Now, introduce the ID of the worker you want to delete from the activity.");
			for (Worker w : listw) {
				System.out.println(w.toStringpartial());
			}
			Integer w_id = Integer.parseInt(consola.readLine());

			sqlm.disconnectActivityWorker(w_id, a_id);
			System.out.println("Deletion done.\n");
		}
	}

	public static void residentActivity() throws IOException {

		int option = 0;
		do {
			System.out.println("Introduce the number:");

			System.out.println("1.Assign an activity to a resident.\n"
					+ "2.Delete a relationship between an activity and a resident.\n"
					+ "3.Return to \"Activities\".\n");
			option = Integer.parseInt(consola.readLine());
			switch (option) {

			case 1:
				addResident2activity();
				break;

			case 2:
				deleteResidentActivity();
				break;

			case 3:
				System.out.println("Going back to \"Activities\".");
				break;
			default:
				break;
			}
		} while (option != 3);
	}

	public static void addResident2activity() throws IOException {

		System.out.println("Introduce the ID of resident you want to assign to an activity.");
		List<Resident> listr = sqlm.selectResidents();
		for (Resident r : listr) {
			System.out.println(r.toStringpartial());
		}

		Integer r_id = Integer.parseInt(consola.readLine());
		System.out.println("Now, introduce the ID of the activity you want to assign to the resident you chose.");

		List<Activity> list = sqlm.selectActivities();
		for (Activity a : list) {
			System.out.println(a.toStringpartial());
		}

		Integer a_id = Integer.parseInt(consola.readLine());

		sqlm.connectActivityResident(r_id, a_id);
		System.out.println("Assignment done.\n");
	}

	public static void deleteResidentActivity() throws IOException {

		System.out.println("Introduce the ID of the activity you want to delete a resident from.");
		List<Activity> list = sqlm.selectActivities();
		for (Activity a : list) {
			System.out.println(a.toStringpartial());
		}
		Integer a_id = Integer.parseInt(consola.readLine());
		List<Resident> listr = sqlm.selectResidentsFromActivity(a_id);
		if (listr.size() == 0) {
			System.out.println("There are no residents in that activity.");
		} else {
			System.out.println("Now, introduce the ID of the resident you want to delete from the activity.");
			for (Resident r : listr) {
				System.out.println(r.toStringpartial());
			}
			Integer r_id = Integer.parseInt(consola.readLine());

			sqlm.disconnectActivityResident(r_id, a_id);
			System.out.println("Deletion done.\n");
		}
	}

/////////////////////////////////////DRUG MENU///////////////////////////////////

	public static void drug() throws IOException {
		int option = 0;
		do {
			System.out.println("Introduce the number:");

			System.out.println("1.New drug.\n" + "2.List of drugs.\n" + "3.Delete.\n" + "4.Search drug.\n"
					+ "5.Return to the main menu.\n");
			option = Integer.parseInt(consola.readLine());
			switch (option) {

			case 1:
				newDrug();
				break;

			case 2:
				listDrugs();
				break;

			case 3:
				deleteDrug();
				break;

			case 4:
				searchDrug();
				break;

			case 5:
				System.out.println("Going back to the menu.");
				break;
			default:
				break;
			}
		} while (option != 5);

	}

	public static void newDrug() throws IOException {

		System.out.println("Introduce the name of the drug.");
		String name = consola.readLine();
		createDrug(name);
	}

	public static void deleteDrug() throws IOException {

		List<Drug> list = sqlm.selectDrugs();
		for (Drug d : list) {
			System.out.println(d);
		}
		System.out.println("Choose a drug to delete, type its ID: ");
		Integer id = Integer.parseInt(consola.readLine());
		sqlm.deleteDrug(id);
		System.out.println("Deletion completed.");

	}

	public static void listDrugs() throws IOException {
		System.out.println("Showing the drugs.");
		List<Drug> list = sqlm.selectDrugs();
		for (Drug d : list) {
			System.out.println(d);
		}

	}

	public static void searchDrug() throws IOException {
		System.out.println("Introduce the name of the drug you want to check if it's stored.");
		String name = consola.readLine();
		if (sqlm.searchDrugByName(name)) {
			System.out.println("The drug " + name + " already exists in the database.");
		} else {
			System.out.println(
					"The drug " + name + " doesn't exist in the database.\n" + "Do you want to create it? Y/N");
			String option = consola.readLine();
			if (option.equalsIgnoreCase("Y")) {
				createDrug(name);
				System.out.println("The drug " + name + " was created.\n");
			} else {
				System.out.println("The drug " + name + " wasn't created.\n");
			}

		}
	}

	public static void createDrug(String name) {
		Drug d = new Drug(name);
		sqlm.insertDrug(d);
		System.out.println("You have created succesfully the drug " + name + ".\n");
	}

/////////////////////////////////////TREATMENT MENU///////////////////////////////////

	public static void treatment() throws IOException {
		int option = 0;
		do {
			System.out.println("Introduce the number:");

			System.out.println("1.New treatment.\n" + "2.List all treatments.\n" + "3.Details of one treatment.\n"
					+ "4.Update treatment.\n" + "5.Delete treatment.\n" + "6.Manage drugs of the treatments"
					+ "\n7.Return to the main menu.");
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
				drugsTreatment();
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
		if (sqlm.selectResidents().size() == 0) {
			System.out.println("You first need to have at least one resident created to create a new treatment.");
		} 
		else if(sqlm.selectDrugs().size() == 0) {
			System.out.println("You first need to have at least one drug created to create a new treatment.");
		} 
		else {
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
			System.out.println(r.toStringpartial());
		}
		System.out.println("Type the id of the resident you want to assign the treatment.\n");
		Integer id = Integer.parseInt(consola.readLine());
		Resident r = sqlm.getResident(id);
		Treatment t = new Treatment(name, start_date, end_date, r);
		System.out.println("Type the id of the drug you want to assign to the treatment.");
		List<Drug> listd = sqlm.selectDrugs();
		for (Drug d : listd) {
			System.out.println(d);
		}
		Integer id_drug = Integer.parseInt(consola.readLine());
		System.out.println("Type the dosage of that drug.");
		String dosage = consola.readLine();
		sqlm.insertTreatment(t, id_drug, dosage);
		System.out.println("Treatment succesfully created.\n");
		}
	}

	public static void infoTreatment() throws IOException {

		System.out.println("Showing the treatments.");
		List<Treatment> list = sqlm.selectTreatments();
		for (Treatment t : list) {
			System.out.println(t.toStringpartial());
		}
	}

	public static void detailsTreatment() throws IOException {

		List<Treatment> list = sqlm.selectTreatments();
		for (Treatment t : list) {
			System.out.println(t.toStringpartial());
		}
		System.out.println("Type the id of the treatment to see in detail.");
		Integer id = Integer.parseInt(consola.readLine());
		Treatment t = sqlm.getTreatment(id);
		System.out.println(t);
		List<Drug> drugs = sqlm.selectDrugsFromTreatment(id);
		for (Drug d : drugs) {
			System.out.println(d.getName() + ":" + sqlm.selectDosageFromTreatment(id, d.getId()));
		}
	}

	public static void updateTreatment() throws IOException {

		List<Treatment> treatments = sqlm.selectTreatments();
		for (Treatment t1 : treatments) {
			List<Drug> drugs = sqlm.selectDrugsFromTreatment(t1.getId());
			for (Drug d : drugs) {
				System.out.println(d.getName() + ":" + sqlm.selectDosageFromTreatment(t1.getId(), d.getId()));
			}
			System.out.println("Choose a treatment, type its ID: ");
			Integer id = Integer.parseInt(consola.readLine());
			String answer, dosage;
			Treatment t = sqlm.getTreatment(id);
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
			Integer id_drug = 0;
			if (answer.equalsIgnoreCase("Y")) {
				List<Drug> listd = sqlm.selectDrugsFromTreatment(id);
				System.out.println("Type the id of the drug you want to change the dosage:");
				for (Drug d : listd) {

					System.out.println(d);

				}
				id_drug = Integer.parseInt(consola.readLine());
				System.out.print("Type the new treatment's dosage: ");
				dosage = consola.readLine();
				sqlm.updateTreatment(t, id_drug, dosage);

			} else {
				dosage = sqlm.selectDosageFromTreatment(t.getId(), id_drug);
				sqlm.updateTreatment(t, id_drug, dosage);
			}

			System.out.println("Treatment updated:\n" + t);
			for (Drug d : drugs) {
				System.out.println(d.getName() + ":" + sqlm.selectDosageFromTreatment(id, d.getId()));
			}
		}

	}

	public static void deleteTreatment() throws IOException {

		List<Treatment> list = sqlm.selectTreatments();
		for (Treatment t : list) {
			System.out.println(t);
		}
		System.out.println("Choose a treatment to delete, type its ID: ");
		Integer id = Integer.parseInt(consola.readLine());
		sqlm.deleteTreatment(id);
		System.out.println("Deletion completed.");

	}

	public static void drugsTreatment() throws IOException {

		int option = 0;
		do {
			System.out.println("Introduce the number:");

			System.out.println("1.Add a drug to a treatment.\n" + "2.Delete a drug from a treatment.\n"
					+ "3.Return to \"Treatment\".\n");
			option = Integer.parseInt(consola.readLine());
			switch (option) {

			case 1:
				addDrug2Treatment();
				break;

			case 2:
				deleteDrugTreatment();
				break;

			case 3:
				System.out.println("Going back to \"Treatment\".");
				break;
			default:
				break;
			}
		} while (option != 3);
	}

	public static void addDrug2Treatment() throws IOException {
		List<Treatment> treatments = sqlm.selectTreatments();
		for (Treatment t1 : treatments) {
			List<Drug> drugs = sqlm.selectDrugsFromTreatment(t1.getId());
			for (Drug d : drugs) {
				System.out.println(d.getName() + ":" + sqlm.selectDosageFromTreatment(t1.getId(), d.getId()));// TODO
																												// ver
																												// si va
			}
		}
		System.out.println("Type the ID of the treatment you want to add a drug");
		int id_treat = Integer.parseInt(consola.readLine());
		int counter, id_drug;
		do {
			counter = 0;
			List<Drug> alldrugs = sqlm.selectDrugs();
			for (Drug d : alldrugs) {
				System.out.println(d);
			}
			System.out.println("Type the ID of the drug you want to add");
			id_drug = Integer.parseInt(consola.readLine());
			List<Drug> drugs_treat = sqlm.selectDrugsFromTreatment(id_treat);
			for (Drug d : drugs_treat) {
				if (d.getId() == id_drug) {
					System.out.println("That drug is already assigned to the treatment. Please, select another:");
					counter++;
				}
			}

		} while (counter != 0);
		System.out.println("Finally, introduce the dosage of the drug.");
		String dosage = consola.readLine();
		sqlm.connectDrugTreatment(id_drug, id_treat, dosage);

		System.out.println("Drug added to treatment.");

	}

	public static void deleteDrugTreatment() throws IOException {
		List<Treatment> treatments = sqlm.selectTreatments();
		for (Treatment t1 : treatments) {
			List<Drug> drugs = sqlm.selectDrugsFromTreatment(t1.getId());
			for (Drug d : drugs) {
				System.out.println(d.getName() + ":" + sqlm.selectDosageFromTreatment(t1.getId(), d.getId()));// TODO
																												// ver
																												// si va
			}
		}
		System.out.println("Type the ID of the treatment you want to delete a drug");
		int id_treat = Integer.parseInt(consola.readLine());
		List<Drug> drugs_treat = sqlm.selectDrugsFromTreatment(id_treat);
		if (drugs_treat.size() == 1) {
			System.out.println(
					"That treatment only has 1 drug. You can delete the whole treatment if you want to in the main menu of \"Treatment\".");
		} else {

			for (Drug d : drugs_treat) {
				System.out.println(d);
			}
			System.out.println("Type the ID of the drug you want to remove:");
			int id_drug = Integer.parseInt(consola.readLine());
			sqlm.disconnectDrugTreatment(id_drug, id_treat);
			System.out.println("Drug removed from the treatment.");
		}
	}
/////////////////////////////////////XML MENU///////////////////////////////////

	public static void xml() throws IOException, JAXBException {
		int option = 0;
		do {
			System.out.println("Introduce the number:");

			System.out.println(
					"1.Marshall rooms.\n2.Unmarshall rooms.\n3.Marshall activities.\n4.Unmarshall activities.\n5.HTML convertion.\n6.Back to menu.");
			option = Integer.parseInt(consola.readLine());
			switch (option) {

			case 1:
				marshallRooms();
				break;
			case 2:
				unmarshallRooms();
				break;
			case 3:
				marshallActivities();
				break;

			case 4:
				unmarshallActivities();
				break;
			case 5:
				generateHTML();
				break;
			case 6:
				System.out.println("Going back to the menu.");
				break;
			default:
				break;
			}
		} while (option != 6);

	}

	public static void marshallRooms() throws IOException, JAXBException {
		System.out.println("Type how do you want to name the XML document (including \"xml\")");
		String name = consola.readLine();
		System.out.println("Marshalling all rooms with their residents...");
		List<Room> list = em.selectRooms();
		Room_list rooms = new Room_list(list);
		xm.marshallRooms(rooms, name);
	}

	public static void marshallActivities() throws IOException, JAXBException {
		System.out.println("Type how do you want to name the XML document (including \"xml\")");
		String name = consola.readLine();
		System.out.println("Marshalling all activities...");
		List<Activity> list = sqlm.selectActivities();
		Activity_list activities = new Activity_list(list);
		xm.marshallActivities(activities, name);
	}

	public static void unmarshallRooms() throws IOException, JAXBException {

		// Pretty formatting
		System.out.println("Introduce what XML file you want to unmarshall");
		String name = consola.readLine();
		Room_list rooms = xm.unmarshallRooms(name);

		for (Room r : rooms.getRooms()) {

			for (Resident res : r.getResidents()) {
				em.insertResident(res);
				res.setRoom(r);
			}
			em.insertRoom(r);
		}
	}

	public static void unmarshallActivities() throws IOException, JAXBException {

		System.out.println("Introduce what XML file you want to unmarshall");
		String name = consola.readLine();
		Activity_list activities = xm.unmarshallActivities(name);
		for (Activity a : activities.getActivities()) {
			sqlm.insertActivity(a);
		}

	}

	public static void generateHTML() throws IOException {
		System.out.println("Introduce the name of the XML file (./xmls/.......xml):");
		String s1 = consola.readLine();
		System.out.println("Introduce the name of the XSLT file (./xmls/.......xslt):");
		String s2 = consola.readLine();
		System.out.println("Introduce the name of the HTML file you want to create (./xmls/.......html)");
		String s3 = consola.readLine();
		xm.simpleTransform(s1, s2, s3);

	}

	public static Date transform_date(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate final_date = LocalDate.parse(date, formatter);
		return Date.valueOf(final_date);
	}
}
