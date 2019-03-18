package nursing_home.db.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nursing_home.pojos.Worker;
//add DOB to sql
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
					" dob DATE," +
					" salary INTEGER NOT NULL)";
			//	" photo BLOB)";
			stmt1.executeUpdate(sql1);
			stmt1.close();
		}
		catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
	
	public void insertWorker(Worker w) {
		try {
		String sql = "INSERT INTO worker (name, job , hire_date, dob, salary) " //falta foto
				+ "VALUES (?,?,?,?);";
		PreparedStatement prep = c.prepareStatement(sql);
		prep.setString(1, w.getName());
		prep.setString(2, w.getJob());
		prep.setDate(3,  w.getHire_date());
		prep.setDate(4, w.getDob());
		prep.setDouble(5, w.getSalary());
		prep.executeUpdate();
		prep.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		 
	}
	
	public List<Worker> selectWorkers() {
		try {
			
			Statement stmt = c.createStatement();
			String sqltext = "SELECT * FROM worker";
			ResultSet rs = stmt.executeQuery(sqltext);
			List<Worker> workerList= new ArrayList<Worker>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String job = rs.getString("job");
				Date hire_date = rs.getDate("hire_date");
				Date dob = rs.getDate("dob");
				Double salary = rs.getDouble("salary");
				Worker worker = new Worker(id, name, job, hire_date, dob, salary);
				workerList.add(worker);
				
			}
			rs.close();
			stmt.close();
			return workerList;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public Worker getWorker(Integer id) {
		try {
			String s= "SELECT * FROM worker WHERE id=?";
			PreparedStatement p= c.prepareStatement(s);
			p.setInt(1, id);
			ResultSet rs= p.executeQuery();
			Worker w = null;
			while(rs.next()){
				Integer w_id= rs.getInt("id");
				String name= rs.getString("name");
				String job= rs.getString("job");
				Date hire_date = rs.getDate("hire_date");
				Date dob = rs.getDate("dob");
				Double salary = rs.getDouble("salary");	
				w= new Worker(w_id, name, job, hire_date, dob, salary);
			}
			return w;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void deleteWorker(Integer id)  {
		try {
			String sql = "DELETE FROM worker WHERE id=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			prep.executeUpdate();		
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public void updateWorker(Worker w) {
		try {
			String sql = "UPDATE worker SET name=?,job=?,salary=?  WHERE id=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, w.getName());
			prep.setString(2, w.getJob());
			prep.setDouble(3, w.getSalary());
			prep.setInt(4, w.getId());

			prep.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

	
	
}