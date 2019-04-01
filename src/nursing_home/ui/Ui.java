package nursing_home.ui;

import java.io.BufferedReader;
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
import nursing_home.pojos.Worker;

public class Ui {
	public static SQLManager sqlm = new SQLManager();

	public static void main(String[] args) throws NumberFormatException, IOException {

		sqlm.connect();
		sqlm.create();
		BufferedReader consola = new BufferedReader(new InputStreamReader(System.in));
		Integer opcion = 0;
		do {
			System.out.println("Introduce the number");

			System.out.println("1.New worker.\n" + "2.Select.\n"+"3.See details of 1 worker."+"4.Delete.\n" +"5.Update.\n"+ "6.Salir.");
			opcion = Integer.parseInt(consola.readLine());
			switch (opcion) {
			case 1:

				System.out.println("Introduce the name");
				String name = consola.readLine();

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
				String fileName= consola.readLine();;
				

				File photo = new File("./photos/" + fileName);
				InputStream streamBlob = new FileInputStream(photo); 

				byte[] bytesBlob = new byte[streamBlob.available()];
				Worker w1 = new Worker(name, job, final_date,dob, salary,bytesBlob);
				sqlm.insertWorker(w1);

				break;
				
			case 2:
				System.out.println("Showing the workers.");
				System.out.println(sqlm.selectWorkers());
				System.out.println("Search finished.");
				break;
				
			case 3:
				List<Worker> list = sqlm.selectWorkers();
				for(Worker worker: list) {
					System.out.println(worker.toStringpartial());
				}
				System.out.println("Type the name of the worker to see in detail.");
				
				Integer id = Integer.parseInt(consola.readLine());
				
				
			case 4:
				System.out.println(sqlm.selectWorkers());
				System.out.println("Choose a worker to delete, type its ID: ");
				int id = Integer.parseInt(consola.readLine());
				sqlm.deleteWorker(id);
				System.out.println("Deletion completed.");
				break;
				
			case 5: 
				System.out.println(sqlm.selectWorkers());
				System.out.println("Choose a worker, type its ID: ");
				id = Integer.parseInt(consola.readLine());
				String answer;
				Worker wu=sqlm.getWorker(id);
				System.out.println("Do you want to change the name?");
				System.out.println("Y/N");
				answer = consola.readLine();
				if(answer.equalsIgnoreCase("Y")) {
				System.out.print("Type the new worker's name: ");
				wu.setName(consola.readLine());					
				}
				System.out.println("Do you want to change the job?");
				System.out.println("Y/N");
				answer = consola.readLine();
				if(answer.equalsIgnoreCase("Y")) {
				System.out.print("Type the new worker's job: ");
				wu.setJob(consola.readLine());					
				}
				System.out.println("Do you want to change the salary?");
				System.out.println("Y/N");
				answer = consola.readLine();
				if(answer.equalsIgnoreCase("Y")) {
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
			}
		} while (opcion != 6);

		sqlm.disconnect();
	}

	public static Date transform_date(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate final_date = LocalDate.parse(date, formatter);
		return Date.valueOf(final_date);
	}

}
