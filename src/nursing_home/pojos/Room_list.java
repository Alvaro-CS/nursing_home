package nursing_home.pojos;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Room_list")
@XmlType(propOrder = {"rooms" })

public class Room_list {
	@XmlElement
	private List<Room> rooms;


	@Override
	public String toString() {
		return "Room_list [rooms=" + rooms + "]";
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public Room_list(List<Room> rooms) {
		super();
		this.rooms = rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public Room_list() {
		super();
	}
	
}
