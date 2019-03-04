package nursing_home.db.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nursing_home.pojos.Worker;

public class SQLManager {
	
	public SQLManager() {
		super();
	}
	
	private Connection c;
	public void connect() {
		try {
			// Open database connection
			Class.forName("org.sqlite.JDBC");//chooses the database type we are using
			this.c = DriverManager.getConnection("jdbc:sqlite:./db/nursing_home.db");//this connects to the database
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened.");
		}catch (Exception e) {
				e.printStackTrace();
		}
	}
		
	public void disconnect() {
		try {
			c.close();
			System.out.println("Database connection closed.");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void create() {
		try {
			Statement stmt1;
			stmt1 = c.createStatement();
			//ON UPDATE-CASCADE
			String sql1 = "CREATE TABLE worker " + 
					"(id INTEGER PRIMARY KEY AUTOINCREMENT," + 
					" name TEXT NOT NULL," + 
					" job TEXT NOT NULL," + 
					" hire_date DATE," + 
					" salary INTEGER NOT NULL)";
			//	" photo BLOB)";
			stmt1.executeUpdate(sql1);
			stmt1.close();
		}
		catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
	
	public Worker insertWorker(Worker w) {
		try {
		String sql = "INSERT INTO worker (name, job , hire_date , salary) " //falta foto
				+ "VALUES (?,?,?,?);";
		PreparedStatement prep = c.prepareStatement(sql);
		prep.setString(1, w.getName());
		prep.setString(2, w.getJob());
		prep.setDate(3,  w.getHire_date());
		prep.setDouble(4, w.getSalary());
		prep.executeUpdate();
		prep.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return w;//MIRAR QUE DEVUELVE 
	}
	
	public void selectWorker() {//VER SI ESTO FUFA
		try {
		Statement stmt = c.createStatement();
		String sqltext = "SELECT * FROM worker";
		ResultSet rs = stmt.executeQuery(sqltext);
		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String job = rs.getString("job");
			Date hire_date = rs.getDate("hire_date");
			Double salary = rs.getDouble("salary");
			Worker worker = new Worker(id, name, job, hire_date, salary);//corregir esto
			System.out.println(worker);
		}
		rs.close();
		stmt.close();
		System.out.println("Search finished.");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	
}