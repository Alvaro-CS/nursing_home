package nursing_home.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ui {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader consola=new BufferedReader(new InputStreamReader(System.in));
		Integer opcion=0;
		System.out.println("Introduce the number");
		
		System.out.println("1.New worker");
		opcion=Integer.parseInt(consola.readLine());
		switch (opcion) {
		case 1:
		{
			
			System.out.println("Introduce the name");
			String name=consola.readLine();
		
			System.out.println("Introduce the job");
			String job=consola.readLine();
			
			System.out.println("Introduce the hire date(''yyyy-mm-dd'')");
			
			String hire_date=consola.readLine();
			Date final_date= transform_date(hire_date);
			
			System.out.println("Introduce the salary");
			
			Double salary=Double.parseDouble(consola.readLine());
			
			/*System.out.println("Introduce the salary");
			
			Double salary=Double.parseDouble(consola.readLine());*/
		}
			break;

		default:
			break;
		}

	}
	
	public static Date transform_date (String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate final_date = LocalDate.parse(date, formatter);
		return Date.valueOf(final_date);
	}

}
