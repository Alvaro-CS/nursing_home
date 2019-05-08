package nursing_home.pojos;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Room_list")
@XmlType(propOrder = {"rooms" })

public class Room_list {
	@XmlAttribute
	private Integer id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room_list other = (Room_list) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public Room_list() {
		super();
	}
	
}
