package nursing_home.interfaces;

import java.util.List;

import nursing_home.pojos.Activity;
import nursing_home.pojos.Drug;
import nursing_home.pojos.Resident;
import nursing_home.pojos.Room;
import nursing_home.pojos.Treatment;
import nursing_home.pojos.Worker;

public interface DBManager {
	public void connect();
	public void disconnect();
	public void create();
	public void insertWorker(Worker w);
	public void insertResident(Resident r);
	public void insertRoom(Room r);
	public void insertActivity(Activity a);
	public void insertDrug(Drug d);
	public void insertTreatment(Treatment t, Drug drug, String dosage);
	public List<Worker> selectWorkers();
	public List<Resident> selectResidents();
	public List<Room> selectRooms();
	public Room getRoom (Integer id);
	public List<Activity> selectActivities();
	public List<Drug> selectDrugs();
	public Worker getWorker(Integer id);
	public Resident getResident(Integer id);
	public void deleteWorker(Integer id);
	public void deleteResident(Integer id);
	public void deleteRoom(Integer id);
	public void deleteActivity(Integer id);
	public void deleteDrug(Integer id);
	public void deleteTreatment(Integer id);
	public void updateWorker(Worker w);
	public void updateResident(Resident r);
	public void updateRoom(Room r);
	public void updateActivity(Activity a);
	public void insertResidentRoom(Room r, Resident re);
	
	
}
