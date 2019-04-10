package nursing_home.interfaces;

import nursing_home.pojos.Resident;
import nursing_home.pojos.Room;

public interface DBManager {
	public void connect();
	public void disconnect();
	public void insertResidentRoom(Room r, Resident re);
	
}
