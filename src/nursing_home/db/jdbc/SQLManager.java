package nursing_home.db.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import nursing_home.pojos.Resident;
import nursing_home.pojos.Worker;
import nursing_home.pojos.Room;
import nursing_home.pojos.Treatment;
import nursing_home.interfaces.DBManager;
import nursing_home.pojos.Activity;
import nursing_home.pojos.Drug;

//ADD ON UPDATE/ON DELETE
public class SQLManager implements DBManager {

	public SQLManager() {
		super();
	}

	private Connection c;

	public void connect() {
		try {
			// Open database connection
			Class.forName("org.sqlite.JDBC");// chooses the database type we are using
			this.c = DriverManager.getConnection("jdbc:sqlite:./db/nursing_home.db");// this connects to the database
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			c.close();
			System.out.println("Database connection closed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void create() {
		try {

			Statement stmt1 = c.createStatement();
			// ON UPDATE-CASCADE ASK RODRIGO IF IT CHANGES ALSO IN THE TABLES. ALSO HOW TO
			// CODE IT HERE.
			String sql1 = "CREATE TABLE workers " + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + " name TEXT NOT NULL,"
					+ " gender TEXT," + " job TEXT NOT NULL," + " hire_date DATE," + " dob DATE,"
					+ " salary INTEGER NOT NULL," + " photo BLOB)";
			stmt1.executeUpdate(sql1);
			stmt1.close();

			Statement stmt2 = c.createStatement();
			// ON UPDATE-CASCADE
			String sql2 = "CREATE TABLE residents " + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT NOT NULL ,"
					+ "gender TEXT," + " dob DATE," + "telephone INTEGER," + "grade TEXT NOT NULL," + "checkin DATE,"
					+ "notes TEXT," + "room_id INTEGER," + "photo BLOB,"
					+ "FOREIGN KEY(room_id) REFERENCES rooms (id))";
			stmt2.executeUpdate(sql2);
			stmt2.close();

			Statement stmt3 = c.createStatement();
			String sql3 = "CREATE TABLE rooms (" + "id INTEGER PRIMARY KEY AUTOINCREMENT," + "roomtype TEXT NOT NULL,"
					+ "floor INTEGER," + "gender TEXT," + "notes TEXT)";
			stmt3.executeUpdate(sql3);
			stmt3.close();

			Statement stmt4 = c.createStatement();
			String sql4 = "CREATE TABLE activities " + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT NOT NULL,"
					+ "days TEXT NOT NULL," + "hours TEXT NOT NULL," + "location TEXT)";
			stmt4.executeUpdate(sql4);
			stmt4.close();

			Statement stmt5 = c.createStatement();
			String sql5 = "CREATE TABLE drugs " + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT NOT NULL)";
			stmt5.execute(sql5);
			stmt5.close();

			Statement stmt6 = c.createStatement();
			String sql6 = "CREATE TABLE treatments " + "(id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT NOT NULL,"
					+ "ini_date TIMESTAMP," + "end_date TIMESTAMP," + "id_resident INTEGER,"
					+ "FOREIGN KEY(id_resident) REFERENCES resident (id))";
			stmt6.executeUpdate(sql6);
			stmt6.close();

			Statement stmt7 = c.createStatement();
			String sql7 = "CREATE TABLE drug_treatment " + "(id_drug INTEGER," + "id_treatment INTEGER,"
					+ "dosage TEXT NOT NULL," + "FOREIGN KEY(id_treatment) REFERENCES treatments (id), "
					+ "FOREIGN KEY (id_drug) REFERENCES drugs (id), PRIMARY KEY (id_drug,id_treatment))";

			stmt7.executeUpdate(sql7);
			stmt7.close();

			Statement stmt8 = c.createStatement();
			String sql8 = "CREATE TABLE activity_distribution " + "(id_worker INTEGER," + "id_activity INTEGER,"
					+ "FOREIGN KEY(id_worker) REFERENCES worker (id) "
					+ "FOREIGN KEY (id_activity) REFERENCES activity (id)," + "PRIMARY KEY (id_worker, id_activity))";

			stmt8.executeUpdate(sql8);
			stmt8.close();

			Statement stmt9 = c.createStatement();
			String sql9 = "CREATE TABLE activity_resident " + "(id_resident INTEGER," + "id_activity INTEGER,"
					+ "FOREIGN KEY(id_resident) REFERENCES resident (id) "
					+ "FOREIGN KEY (id_activity) REFERENCES activity (id)," + "PRIMARY KEY (id_resident, id_activity))";
			stmt9.executeUpdate(sql9);
			stmt9.close();

			Statement stmt10 = c.createStatement();
			String sql10 = "CREATE TABLE worker_distribution" + "(id_worker INTEGER," + "id_resident INTEGER,"
					+ "FOREIGN KEY(id_worker) REFERENCES worker (id) "
					+ "FOREIGN KEY (id_resident) REFERENCES resident (id) ON DELETE CASCADE,"
					+ "PRIMARY KEY (id_worker, id_resident))";
			stmt10.executeUpdate(sql10);
			stmt10.close();

			Statement stmtSeq = c.createStatement();

			String sqlSeq = "INSERT INTO sqlite_sequence (name, seq) VALUES ('rooms', 1)";
			stmtSeq.executeUpdate(sqlSeq);

			sqlSeq = "INSERT INTO sqlite_sequence (name, seq) VALUES ('residents', 1)";
			stmtSeq.executeUpdate(sqlSeq);
			stmtSeq.close();

		} catch (SQLException e) {
			if (e.getMessage().contains("already exists")) {
				System.out.println("Tables are already created");
			} else {
				e.printStackTrace();
			}
		}
	}

	public void insertWorker(Worker w) {
		try {

			String sql = "INSERT INTO workers (name, gender, job , hire_date, dob, salary, photo) "
					+ "VALUES (?,?,?,?,?,?,?);";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, w.getName());
			prep.setString(2, w.getGender());
			prep.setString(3, w.getJob());
			prep.setDate(4, w.getHire_date());
			prep.setDate(5, w.getDob());
			prep.setDouble(6, w.getSalary());
			prep.setBytes(7, w.getPhoto());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void connectResidentWorker(Integer w_id, Integer r_id) {
		try {

			String sql = "INSERT INTO worker_distribution (id_worker,id_resident) " + "VALUES (?,?);";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, w_id);
			prep.setInt(2, r_id);
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertResident(Resident r) {
		try {

			String sql = "INSERT INTO residents (name, gender, dob , telephone, grade, checkin,notes, room_id, photo) "
					+ "VALUES (?,?,?,?,?,?,?,?,?);";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, r.getName());
			prep.setString(2, r.getGender());
			prep.setDate(3, r.getDob());
			prep.setInt(4, r.getTeleph());
			prep.setString(5, r.getDep_grade());
			prep.setDate(6, r.getCheckin());
			prep.setString(7, r.getNotes());
			prep.setInt(8, r.getRoom().getId());
			prep.setBytes(9, r.getPhoto());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertRoom(Room r) {
		try {
			String sql = "INSERT INTO rooms (roomtype, floor,gender,notes)" + "VALUES (?,?,?,?);";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, r.getRoomtype());
			prep.setInt(2, r.getFloor());
			prep.setString(3, r.getGender());
			prep.setString(4, r.getNotes());
			prep.executeUpdate();
			prep.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertActivity(Activity a) {
		try {
			String sql = "INSERT INTO activities (name,hours,days,location)" + "VALUES (?,?,?,?);";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, a.getName());
			prep.setString(2, a.getHours());
			prep.setString(3, a.getDays());
			prep.setString(4, a.getLocation());

			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertDrug(Drug d) {
		try {
			String sql = "INSERT INTO drugs (name) VALUES (?);";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, d.getName());
			prep.executeUpdate();
			prep.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertTreatment(Treatment t, Integer id_drug, String dosage) {
		try {
			String sql = "INSERT INTO treatment () VALUES (?,?,?,?,?,?);";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, t.getId());
			prep.setString(2, t.getName());
			prep.setDate(3, t.getInitial_date());
			prep.setDate(4, t.getFinal_date());
			prep.setInt(5, t.getResident().getId());
			prep.setInt(5, 1);
			prep.setInt(3, 1);
			prep.executeUpdate();
			prep.close();

			// Get the ID of treatment
			String query = "SELECT last_insert_rowid() AS lastId";
			PreparedStatement p1 = c.prepareStatement(query);
			ResultSet rs = p1.executeQuery();
			Integer lastId = rs.getInt("lastId");
			// Insert into drug_treatment the ID of the treatment
			// and the ID of the drug
			PreparedStatement p2 = c.prepareStatement(
					"INSERT INTO drug_treatment" + " (id_treatment, id_drug, dosage)" + " VALUES (?,?,?)");
			p2.setInt(1, lastId);
			p2.setInt(2, id_drug);
			p2.setString(3, dosage);
			p2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Worker> selectWorkers() {
		try {

			Statement stmt = c.createStatement();
			String sqltext = "SELECT * FROM workers";
			ResultSet rs = stmt.executeQuery(sqltext);
			List<Worker> workerList = new ArrayList<Worker>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				String job = rs.getString("job");
				Date hire_date = rs.getDate("hire_date");
				Date dob = rs.getDate("dob");
				Double salary = rs.getDouble("salary");
				byte[] photo = rs.getBytes("photo");
				Worker worker = new Worker(id, name, gender, job, hire_date, dob, salary, photo);
				workerList.add(worker);

			}
			rs.close();
			stmt.close();
			return workerList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public Worker getWorker(Integer id) {
		try {
			String s = "SELECT * FROM workers WHERE id=?";
			PreparedStatement p = c.prepareStatement(s);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			Worker w = null;
			while (rs.next()) {
				Integer w_id = rs.getInt("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				String job = rs.getString("job");
				Date hire_date = rs.getDate("hire_date");
				Date dob = rs.getDate("dob");
				Double salary = rs.getDouble("salary");
				byte[] photo = rs.getBytes("photo");
				w = new Worker(w_id, name, gender, job, hire_date, dob, salary, photo);
			}
			return w;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Resident> selectResidents() {
		try {

			Statement stmt = c.createStatement();
			String sqltext = "SELECT * FROM residents";
			ResultSet rs = stmt.executeQuery(sqltext);
			List<Resident> residentList = new ArrayList<Resident>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				Date dob = rs.getDate("dob");
				int teleph = rs.getInt("telephone");
				String dep_grade = rs.getString("grade");
				Date checkin = rs.getDate("checkin");
				byte[] photo = rs.getBytes("photo");
				String notes = rs.getString("notes");
				int room_id = rs.getInt("room_id");
				Room room = getRoom(room_id);
				Resident resident = new Resident(id, name, gender, dob, teleph, dep_grade, checkin, photo, notes, room);
				residentList.add(resident);

			}
			rs.close();
			stmt.close();
			return residentList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public Resident getResident(Integer id) {
		try {
			String s = "SELECT * FROM residents WHERE id=?";
			PreparedStatement p = c.prepareStatement(s);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			Resident r = null;
			while (rs.next()) {
				Integer r_id = rs.getInt("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				Date dob = rs.getDate("dob");
				Integer telephone = rs.getInt("telephone");
				String dep_grade = rs.getString("grade");
				Date checkin = rs.getDate("checkin");
				byte[] photo = rs.getBytes("photo");
				String notes = rs.getString("notes");
				int room_id = rs.getInt("room_id");
				Room room = getRoom(room_id);
				r = new Resident(r_id, name, gender, dob, telephone, dep_grade, checkin, photo, notes, room);
			}
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<Resident> selectResidentsFromWorker(Integer idworker) {
		try {

			// Statement stmt = c.createStatement();
			String sqltext = "SELECT r.id,r.name,r.gender,r.dob,r.telephone,r.grade,r.checkin,r.photo,r.notes,r.room_id "
					+ "FROM worker_distribution AS w JOIN residents AS r " + "ON w.id_resident=r.id "
					+ "WHERE w.id_worker=?";
			PreparedStatement p = c.prepareStatement(sqltext);
			p.setInt(1, idworker);
			ResultSet rs = p.executeQuery();
			List<Resident> residentList = new ArrayList<Resident>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				Date dob = rs.getDate("dob");
				int teleph = rs.getInt("telephone");
				String dep_grade = rs.getString("grade");
				Date checkin = rs.getDate("checkin");
				byte[] photo = rs.getBytes("photo");
				String notes = rs.getString("notes");
				int room_id = rs.getInt("room_id");
				Room room = getRoom(room_id);
				Resident resident = new Resident(id, name, gender, dob, teleph, dep_grade, checkin, photo, notes, room);
				residentList.add(resident);

			}
			rs.close();
			p.close();
			// stmt.close();
			return residentList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public List<Worker> selectWorkersFromResident(Integer idresident) {
		try {

			String sqltext = "SELECT w.id,w.name,w.gender,w.job,w.hire_date,w.dob,w.salary,w.photo "
					+ "FROM worker_distribution AS r LEFT JOIN workers AS w " + "ON r.id_worker=w.id "
					+ "WHERE r.id_resident=?";
			PreparedStatement p = c.prepareStatement(sqltext);
			p.setInt(1, idresident);
			ResultSet rs = p.executeQuery();
			List<Worker> workerList = new ArrayList<Worker>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				String job = rs.getString("job");
				Date hire_date = rs.getDate("hire_date");
				Date dob = rs.getDate("dob");
				Double salary = rs.getDouble("salary");
				byte[] photo = rs.getBytes("photo");
				Worker worker = new Worker(id, name, gender, job, hire_date, dob, salary, photo);
				workerList.add(worker);

			}
			rs.close();
			return workerList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public List<Room> selectRooms() {
		try {
			Statement stmt = c.createStatement();
			String sql = "SELECT * FROM rooms";
			ResultSet rs = stmt.executeQuery(sql);
			List<Room> roomList = new ArrayList<Room>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String roomtype = rs.getString("roomtype");
				int floor = rs.getInt("floor");
				String gender = rs.getString("gender");
				String notes = rs.getString("notes");
				Room room = new Room(id, roomtype, floor, gender, notes);
				roomList.add(room);
			}
			rs.close();
			stmt.close();
			return roomList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Room getRoom(Integer id) {
		try {
			String s = "SELECT * FROM rooms WHERE id=?";
			PreparedStatement p = c.prepareStatement(s);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			Room r = null;
			while (rs.next()) {
				Integer r_id = rs.getInt("id");
				String r_name = rs.getString("roomtype");
				Integer r_floor = rs.getInt("floor");
				String r_gender = rs.getNString("gender");
				String r_notes = rs.getString("notes");

				r = new Room(r_id, r_name, r_floor, r_gender, r_notes);
			}
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Activity> selectActivities() {
		try {
			Statement stmt = c.createStatement();
			String sql = "SELECT * FROM activities";
			ResultSet rs = stmt.executeQuery(sql);
			List<Activity> activityList = new ArrayList<Activity>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String hours = rs.getString("hours");
				String days = rs.getString("days");
				String location = rs.getString("location");
				Activity activity = new Activity(id, name, hours, days, location);
				activityList.add(activity);
			}
			rs.close();
			stmt.close();
			return activityList;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Activity> selectActivitiesFromResident(Integer idresident) {
		try {

			String sqltext = "SELECT a.id,a.name,a.hours,a.days,a.location "
					+ "FROM activity_resident AS ar JOIN activities AS a "
					+ "ON ar.id_activity=a.id "
					+ "WHERE w.id_resident=?";
			PreparedStatement p = c.prepareStatement(sqltext);
			p.setInt(1, idresident);
			ResultSet rs = p.executeQuery();
			List<Activity> activityList = new ArrayList<Activity>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String hours = rs.getString("hours");
				String days = rs.getString("days");
				String location = rs.getString("location");
				Activity activity = new Activity(id, name, hours, days, location);
				activityList.add(activity);

			}
			rs.close();
			p.close();
			return activityList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
	public List<Worker> selectWorkersFromActivity(Integer idactivity) {
		try {

			String sqltext = "SELECT w.id,w.name,w.gender,w.job,w.hire_date,w.dob,w.salary,w.photo "
					+ "FROM activity_distribution AS ad JOIN workers AS w "
					+ "ON ad.id_worker=w.id "
					+ "WHERE ad.id_activity=?";
			PreparedStatement p = c.prepareStatement(sqltext);
			p.setInt(1, idactivity);
			ResultSet rs = p.executeQuery();
			List<Worker> workerList = new ArrayList<Worker>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				String job = rs.getString("job");
				Date hire_date = rs.getDate("hire_date");
				Date dob = rs.getDate("dob");
				Double salary = rs.getDouble("salary");
				byte[] photo = rs.getBytes("photo");
				Worker worker = new Worker(id, name, gender, job, hire_date, dob, salary, photo);
				workerList.add(worker);

			}
			rs.close();
			return workerList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public List<Activity> selectActivitiesFromWorker(Integer idworker) {
		try {

			String sqltext = "SELECT a.id,a.name,a.hours,a.days,a.location "
					+ "FROM activity_distribution AS ad JOIN activities AS a "
					+ "ON ad.id_activity=a.id "
					+ "WHERE w.id_worker=?";
			PreparedStatement p = c.prepareStatement(sqltext);
			p.setInt(1, idworker);
			ResultSet rs = p.executeQuery();
			List<Activity> activityList = new ArrayList<Activity>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String hours = rs.getString("hours");
				String days = rs.getString("days");
				String location = rs.getString("location");
				Activity activity = new Activity(id, name, hours, days, location);
				activityList.add(activity);

			}
			rs.close();
			p.close();
			return activityList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public List<Resident> selectResidentsFromActivity(Integer idactivity) {
		try {

			String sqltext = "SELECT r.id,r.name,r.gender,r.dob,r.telephone,r.grade,r.checkin,r.photo,r.notes,r.room_id "
					+ "FROM activity_resident AS ar JOIN residents AS r " 
					+ "ON ar.id_resident=r.id "
					+ "WHERE a.id_activity=?";
			PreparedStatement p = c.prepareStatement(sqltext);
			p.setInt(1, idactivity);
			ResultSet rs = p.executeQuery();
			List<Resident> residentList = new ArrayList<Resident>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				Date dob = rs.getDate("dob");
				int teleph = rs.getInt("telephone");
				String dep_grade = rs.getString("grade");
				Date checkin = rs.getDate("checkin");
				byte[] photo = rs.getBytes("photo");
				String notes = rs.getString("notes");
				int room_id = rs.getInt("room_id");
				Room room = getRoom(room_id);
				Resident resident = new Resident(id, name, gender, dob, teleph, dep_grade, checkin, photo, notes, room);
				residentList.add(resident);

			}
			rs.close();
			p.close();
			return residentList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
	public Activity getActivity(Integer id) {
		try {
			String s = "SELECT * FROM activities WHERE id=?";
			PreparedStatement p = c.prepareStatement(s);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			Activity a = null;
			while (rs.next()) {
				Integer a_id = rs.getInt("id");
				String a_name = rs.getString("name");
				String a_hours = rs.getString("hours");
				String a_days = rs.getString("days");
				String a_location = rs.getString("location");

				a = new Activity(a_id, a_name, a_hours, a_days, a_location);
			}
			return a;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Drug> selectDrugs() {
		try {
			Statement stmt = c.createStatement();
			String sql = "SELECT * FROM drugs";
			ResultSet rs = stmt.executeQuery(sql);
			List<Drug> drugList = new ArrayList<Drug>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Drug drug = new Drug(id, name);
				drugList.add(drug);
			}
			rs.close();
			stmt.close();
			return drugList;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Drug getDrug(Integer id) {
		try {
			String s = "SELECT * FROM drugs WHERE id=?";
			PreparedStatement p = c.prepareStatement(s);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			Drug d = null;
			while (rs.next()) {
				Integer d_id = rs.getInt("id");
				String d_name = rs.getString("roomtype");

				d = new Drug(d_id, d_name);
			}
			return d;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Treatment> selectTreatments() {
		try {
			Statement stmt = c.createStatement();
			String sql = "SELECT * FROM treatment";
			ResultSet rs = stmt.executeQuery(sql);
			List<Treatment> treatmentList = new ArrayList<Treatment>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Date ini_date = rs.getDate("ini_date");
				Date end_date = rs.getDate("end_date");
				int id_resident = rs.getInt("id_resident");

				Resident resident = getResident(id_resident);
				Treatment treatment = new Treatment(id, name, ini_date, end_date, resident);
				treatmentList.add(treatment);
			}
			rs.close();
			stmt.close();
			return treatmentList;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Treatment getTreatment(Integer id) {
		try {
			String s = "SELECT * FROM treatments WHERE id=?";
			PreparedStatement p = c.prepareStatement(s);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			Treatment t = null;
			while (rs.next()) {
				Integer t_id = rs.getInt("id");
				String name = rs.getString("name");
				Date ini_date = rs.getDate("ini_date");
				Date end_date = rs.getDate("end_date");
				int resident_id = rs.getInt("id_resident");

				Resident resident = getResident(resident_id);
				t = new Treatment(t_id, name, ini_date, end_date, resident);
			}
			return t;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void deleteWorker(Integer id) {
		try {
			String sql = "DELETE FROM workers WHERE id=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteResident(Integer id) {
		try {
			String sql = "DELETE FROM residents WHERE id=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			prep.executeUpdate();
			prep.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteRoom(Integer id) {
		try {
			String sql = "DELETE FROM room WHERE id=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			prep.executeUpdate();
			prep.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteActivity(Integer id) {
		try {
			String sql = "DELETE FROM activities WHERE id=? ";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			prep.executeUpdate();
			prep.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteDrug(Integer id) {
		try {
			String sql = "DELETE FROM drugs WHERE id=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			prep.executeUpdate();
			prep.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteTreatment(Integer id) {
		try {
			String sql = "DELETE FROM treatments WHERE id=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			prep.executeUpdate();
			prep.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateWorker(Worker w) {
		try {
			String sql = "UPDATE workers SET name=?,job=?,salary=?  WHERE id=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, w.getName());
			prep.setString(2, w.getJob());
			prep.setDouble(3, w.getSalary());
			prep.setInt(4, w.getId());
			prep.executeUpdate();
			prep.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateResident(Resident r) {
		try {
			String sql = "UPDATE residents SET name=?,telephone=?,grade=?, notes=? WHERE id=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, r.getName());
			prep.setInt(2, r.getTeleph());
			prep.setString(3, r.getDep_grade());
			prep.setString(4, r.getNotes());
			prep.setInt(5, r.getId());
			prep.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateRoom(Room r) {
		try {
			String sql = "UPDATE rooms SET roomtype=?,gender=?, notes=? WHERE id=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, r.getRoomtype());
			prep.setString(2, r.getGender());
			prep.setString(3, r.getNotes());
			prep.setInt(4, r.getId());
			prep.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateActivity(Activity a) {
		try {
			String sql = "UPDATE activities SET name=?, hours=?, days=?, location=? WHERE id=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, a.getName());
			prep.setString(2, a.getHours());
			prep.setString(3, a.getDays());
			prep.setString(4, a.getLocation());
			prep.setInt(5, a.getId());
			prep.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateTreatment(Treatment t) {
		try {// TODO
			String sql = "UPDATE treatments SET name=?, end_dates=? WHERE id=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, t.getName());
			prep.setDate(2, t.getFinal_date());
			prep.setInt(3, t.getId());
			prep.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertResidentRoom(Room r, Resident re) {

	}

}