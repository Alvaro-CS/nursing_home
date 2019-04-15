package nursing_home.ui;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
		sqlm.create();

		Integer opcion1 = 0;
		do {
		System.out.println("Select what do you want to manage: \n1.Workers. \n2.Residents. \n3.Exit.");
		opcion1 = Integer.parseInt(consola.readLine());
		switch(opcion1) {
			case 1:
			worker();
			case 2:
		    resident();
		} 
		}
		while (opcion1 != 3);
		
	}

	public static Date transform_date(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate final_date = LocalDate.parse(date, formatter);
		return Date.valueOf(final_date);
	}
	
	
	
	
	
	public static void worker() throws IOException {
		int opcionw = 0;
		do {
		System.out.println("Introduce the number");

		System.out.println("1.New worker.\n" + "2.Basic info.\n" + "3.See details of 1 worker.\n" + "4.Delete.\n"
				+ "5.Update.\n" + "6.Salir.");
		opcionw = Integer.parseInt(consola.readLine());
		switch (opcionw) {
		
		case 1:

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
			Worker w1 = new Worker(name, gender , job, final_date, dob, salary, bytesBlob);
			sqlm.insertWorker(w1);

			break;

		case 2:
			System.out.println("Showing the workers.");
			System.out.println(sqlm.selectWorkers());
			System.out.println("Search finished.");
			break;

		case 3:
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
			break;

		case 4:
			List<Worker> list1 = sqlm.selectWorkers();
			for (Worker worker : list1) {
				System.out.println(worker.toStringpartial());
			}
			System.out.println("Choose a worker to delete, type its ID: ");
			id = Integer.parseInt(consola.readLine());
			sqlm.deleteWorker(id);
			System.out.println("Deletion completed.");
			break;

		case 5:
			System.out.println(sqlm.selectWorkers());
			System.out.println("Choose a worker, type its ID: ");
			id = Integer.parseInt(consola.readLine());
			String answer;
			Worker wu = sqlm.getWorker(id);
			System.out.println("Do you want to change the name?");
			System.out.println("Y/N");
			answer = consola.readLine();
			if (answer.equalsIgnoreCase("Y")) {
				System.out.print("Type the new worker's name: ");
				wu.setName(consola.readLine());
			}
			System.out.println("Do you want to change the job?");
			System.out.println("Y/N");
			answer = consola.readLine();
			if (answer.equalsIgnoreCase("Y")) {
				System.out.print("Type the new worker's job: ");
				wu.setJob(consola.readLine());
			}
			System.out.println("Do you want to change the salary?");
			System.out.println("Y/N");
			answer = consola.readLine();
			if (answer.equalsIgnoreCase("Y")) {
				System.out.print("Type the new worker's salary: ");
				wu.setSalary(Double.parseDouble(consola.readLine()));
			}
			sqlm.updateWorker(wu);
			System.out.println(sqlm.selectWorkers());
			break;
		case 6:
			System.out.println("Exit.");
			break;
		default:
			break;
		}}
	while(opcionw!=6);

	sqlm.disconnect();em.disconnect();
}

	
	
	
	public static void resident() throws IOException {
		int opcionr = 0;
		do {
		System.out.println("Introduce the number");

		System.out.println("1.New resident.\n" + "2.Basic info.\n" + "3.See details of 1 worker.\n" + "4.Delete.\n"
				+ "5.Update.\n" + "6.Salir.");
		opcionr = Integer.parseInt(consola.readLine());
		switch (opcionr) {
		
		case 1:

			System.out.println("Introduce the name");
			String name = consola.readLine();

			System.out.println("Introduce the gender");
			String gender = consola.readLine();
			
			System.out.println("Introduce the birth date(''yyyy-mm-dd'')");

			String birth_date = consola.readLine();
			Date dob = transform_date(birth_date);

			System.out.println("Introduce the contact telephone");
			Integer tel = Integer.parseInt(consola.readLine());
			
			System.out.println("Introduce the grade of dependency");
			String grade = consola.readLine();
			
			System.out.println("Introduce the date of check-in(''yyyy-mm-dd'')");

			String checkin = consola.readLine();
			Date date_check = transform_date(checkin);

			System.out.println("Introduce any extra notes or details about the patient.");
			String notes = consola.readLine();

			System.out.println("Introduce the name of the photo as it appears in the folder");
			String fileName = consola.readLine();
			

			File photo = new File("./photos/" + fileName);
			InputStream streamBlob = new FileInputStream(photo);

			byte[] bytesBlob = new byte[streamBlob.available()];
			streamBlob.read(bytesBlob);
			streamBlob.close();
			
			System.out.println("Now, assign the patient into a room.");
			System.out.println("Rooms available:");

			List<Room> list = sqlm.selectRooms();//ver si funciona
			for (Room r : list) {
				System.out.println(r);
			}
			System.out.println("Type the id of the room you want to assign to the resident.");
			Integer id = Integer.parseInt(consola.readLine());
			Room r= sqlm.getRoom(id);
			Resident r1=new Resident(name,gender,dob,tel,grade,date_check,bytesBlob,notes,r);
			sqlm.insertResident(r1);

			break;

		case 2:
			System.out.println("Showing the residents.");
			System.out.println(sqlm.selectWorkers());
			
			break;

		case 3:
			List<Resident> listr = sqlm.selectResidents();
			for (Resident r2 : listr) {
				System.out.println(r2.toStringpartial());
			}
			System.out.println("Type the id of the worker to see in detail.");
			Integer id1 = Integer.parseInt(consola.readLine());
			Resident r2 = sqlm.getResident(id1);
			System.out.println(r2);// It prints all the info of the person
			// Now, we show the photo
			if (r2.getPhoto() != null) {
				ByteArrayInputStream blobIn = new ByteArrayInputStream(r2.getPhoto());
				ImageWindow window = new ImageWindow();
				window.showBlob(blobIn);
			}
			break;

		case 4:
			List<Resident> listr2 = sqlm.selectResidents();
			for (Resident r3 : listr2) {
				System.out.println(r3.toStringpartial());
			}
			System.out.println("Choose a resident to delete, type its ID: ");
			id = Integer.parseInt(consola.readLine());
			sqlm.deleteResident(id);
			System.out.println("Deletion completed.");
			break;

		case 5:
			System.out.println(sqlm.selectResidents());
			System.out.println("Choose a resident, type its ID: ");
			id = Integer.parseInt(consola.readLine());
			String answer;
			Resident r4 = sqlm.getResident(id);
			System.out.println("Do you want to change the name?");
			System.out.println("Y/N");
			answer = consola.readLine();
			if (answer.equalsIgnoreCase("Y")) {
				System.out.print("Type the new resident's name: ");
				r4.setName(consola.readLine());
			}
			System.out.println("Do you want to change the contact telephone?");
			System.out.println("Y/N");
			answer = consola.readLine();
			if (answer.equalsIgnoreCase("Y")) {
				System.out.print("Type the new resident's contact teleph: ");
				r4.setTeleph(Integer.parseInt((consola.readLine())));
			}
			System.out.println("Do you want to change the grade of dependency?");
			System.out.println("Y/N");
			answer = consola.readLine();
			if (answer.equalsIgnoreCase("Y")) {
				System.out.print("Type the new residents's grade of dependency: ");
				r4.setDep_grade((consola.readLine()));
			}
			
			System.out.println("Do you want to change the notes?");
			System.out.println("Y/N");
			answer = consola.readLine();
			if (answer.equalsIgnoreCase("Y")) {
				System.out.print("Type the new residents's notes: ");
				r4.setNotes(((consola.readLine())));
			}
			
			System.out.println("Do you want to change the resident's room?");
			System.out.println("Y/N");
			answer = consola.readLine();
			if (answer.equalsIgnoreCase("Y")) {
				System.out.print("Type the new residents's room (id): ");
				int room_id=Integer.parseInt(consola.readLine());
				Room ro= sqlm.getRoom(room_id);
				r4.setRoom(ro);
			}
			
			sqlm.updateResident(r4);
			System.out.println(sqlm.selectWorkers());
			break;
		case 6:
			System.out.println("Exit.");
			break;
		default:
			break;
		}}
	while(opcionr!=6);

	sqlm.disconnect();em.disconnect();
}


	
}
