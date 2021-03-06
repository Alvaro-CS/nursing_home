package nursing_home.db.jpa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
		em.getTransaction().commit();
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
		
	}

	@Override
	public void insertWorker(Worker w) {
		
	}

	@Override
	public void insertResident(Resident r) {
		// Begin transaction
		em.getTransaction().begin();
		// Store the object
		em.persist(r);
		// End transaction
		em.getTransaction().commit();
		
	}

	@Override
	public void insertActivity(Activity a) {
		
	}

	@Override
	public void insertDrug(Drug d) {
		
	}

	@Override
	public void insertTreatment(Treatment t, Integer id_drug, String dosage) {
		
	}

	@Override
	public List<Worker> selectWorkers() {
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
	
	public int countResidentsFromRoom(int room) {
		Query q1 = em.createNativeQuery("SELECT COUNT(id) FROM residents WHERE room_id=?"); 
		q1.setParameter(1, room);
		int residents=(int)q1.getSingleResult();
		return residents;
	}

	@Override
	public Room getRoom(Integer id) {
		int room_id = id;
		Query q1 = em.createNativeQuery("SELECT * FROM rooms WHERE id LIKE ?", Room.class);
		q1.setParameter(1, room_id);
		Room room = (Room) q1.getSingleResult();
		return room;
	}

	@Override
	public List<Activity> selectActivities() {
		return null;
	}

	@Override
	public List<Drug> selectDrugs() {
		return null;
	}

	@Override
	public Worker getWorker(Integer id) {
		return null;
	}

	@Override
	public Resident getResident(Integer id) {
		int resident_id = id;
		Query q1 = em.createNativeQuery("SELECT * FROM residents WHERE id LIKE ?", Resident.class);
		q1.setParameter(1, resident_id);
		Resident resident = (Resident) q1.getSingleResult();		
		return resident;
	}

	@Override
	public void deleteWorker(Integer id) {
		
	}

	@Override
	public void deleteResident(Integer id) {

		int resident_id = id;
		Query q2 = em.createNativeQuery("SELECT * FROM residents WHERE id = ?", Resident.class);
		q2.setParameter(1, resident_id);
		Resident r1 = (Resident) q2.getSingleResult();

		em.getTransaction().begin();
		em.remove(r1);
		em.getTransaction().commit();		
	}

	@Override
	public void deleteRoom(Integer id) {

		int room_id = id;
		Query q2 = em.createNativeQuery("SELECT * FROM rooms WHERE id = ?", Room.class);
		q2.setParameter(1, room_id);
		Room r1 = (Room) q2.getSingleResult();

		em.getTransaction().begin();
		em.remove(r1);
		em.getTransaction().commit();

	}

	@Override
	public void deleteActivity(Integer id) {
		
	}

	@Override
	public void deleteDrug(Integer id) {
		
	}

	@Override
	public void deleteTreatment(Integer id) {
		
	}

	@Override
	public void updateWorker(Worker w) {
		
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
		
	}

	@Override
	public void connectResidentWorker(Integer w_id, Integer r_id) {

	}

	@Override
	public List<Resident> selectResidentsFromWorker(Integer idworker) {
		return null;
	}

	@Override
	public List<Worker> selectWorkersFromResident(Integer idresident) {
		return null;
	}

	@Override
	public Activity getActivity(Integer id) {
		return null;
	}

	@Override
	public List<Treatment> selectTreatments() {
		return null;
	}

	@Override
	public Drug getDrug(Integer id) {
		return null;
	}

	@Override
	public Treatment getTreatment(Integer id) {
		return null;
	}


	@Override
	public void updateTreatment(Treatment t, Integer id_drug, String dosage) {
		
	}

	@Override
	public boolean searchDrugByName(String name) {
		return false;
	}

	@Override
	public List<Drug> selectDrugsFromTreatment(Integer id_treatment) {
		return null;
	}

	@Override
	public List<Treatment> selectTreatmentsFromDrug(Integer id_drug) {
		return null;
	}

	@Override
	public List<Activity> selectActivitiesFromResident(Integer idresident) {
		return null;
	}

	@Override
	public List<Worker> selectWorkersFromActivity(Integer idactivity) {
		return null;
	}

	@Override
	public List<Activity> selectActivitiesFromWorker(Integer idworker) {
		return null;
	}

	@Override
	public List<Resident> selectResidentsFromActivity(Integer idactivity) {
		return null;
	}

	@Override
	public void disconnectResidentWorker(Integer w_id, Integer r_id) {
		
	}

	@Override
	public void connectActivityWorker(Integer w_id, Integer a_id) {
		
	}

	@Override
	public void disconnectActivityWorker(Integer w_id, Integer a_id) {
		
	}

	@Override
	public void connectActivityResident(Integer r_id, Integer a_id) {
		
	}

	@Override
	public void disconnectActivityResident(Integer r_id, Integer a_id) {
		
	}


	@Override
	public void disconnectDrugTreatment(Integer d_id, Integer t_id) {

	}

	@Override
	public void connectDrugTreatment(Integer d_id, Integer t_id, String dosage) {

	}
}
