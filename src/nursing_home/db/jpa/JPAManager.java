package nursing_home.db.jpa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import nursing_home.interfaces.DBManager;
import nursing_home.pojos.*;

public class JPAManager implements DBManager {
	EntityManager em;

	public void connect() {

		em = Persistence.createEntityManagerFactory("nursing_home-provider").createEntityManager(); // same name as the one
																								// of persistence.xml
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate(); // to activate foreign keys.
		em.getTransaction().commit();// Jpa doesn't have autocommit
	}

	public void disconnect() {
		em.close();
	}

	public void insertRoom(Room r) {
		// Begin transaction
		em.getTransaction().begin();
		// Store the object
		em.persist(r);
		// End transaction
		em.getTransaction().commit();

	}

	@Override
	public void insertResidentRoom(Room r, Resident re) {
		em.getTransaction().begin();
		re.setRoom(r);
		r.addResident(re);
		em.getTransaction().commit();
		
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertWorker(Worker w) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertResident(Resident r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertActivity(Activity a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertDrug(Drug d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertTreatment(Treatment t, Drug drug, String dosage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Worker> selectWorkers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Resident> selectResidents() {
		Query q1 = em.createNativeQuery("SELECT * FROM residents", Resident.class); //this returns a query object 
		List<Resident> residents = (List<Resident>) q1.getResultList(); //cast because it return a list of objects, but we want a list of residents.
		return residents;
	}

	@Override
	public List<Room> selectRooms () {
		Query q1 = em.createNativeQuery("SELECT * FROM rooms", Room.class);
		List <Room> rooms= (List<Room>) q1.getResultList();
		return rooms;
	}

	@Override
	public Room getRoom(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> selectActivities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Drug> selectDrugs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Worker getWorker(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resident getResident(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteWorker(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteResident(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRoom(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteActivity(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteDrug(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTreatment(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateWorker(Worker w) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateResident(Resident r) {
		// Begin transaction
		em.getTransaction().begin();
		// Make changes
		em.flush();
		// End transaction
		em.getTransaction().commit();
			
	}

	@Override
	public void updateRoom(Room r) {
		
		// Begin transaction
		em.getTransaction().begin();
		// Make changes
		em.flush();
		// End transaction
		em.getTransaction().commit();
		
	}

	@Override
	public void updateActivity(Activity a) {
		// TODO Auto-generated method stub
		
	}
}
