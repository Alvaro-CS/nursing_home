package nursing_home.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

public class Room implements Serializable {
	private Integer id;
	private String roomtype;
	private Integer floor;
	private String gender;
	private String notes;

	public Room() {
		super();
	}

	public Room(Integer id, String roomtype, Integer floor, String notes, String gender) {
		super();
		this.id = id;
		this.roomtype = roomtype;
		this.floor = floor;
		this.notes = notes;
		this.gender = gender;
	}

	public Room(String roomtype, Integer floor, String notes, String gender) {
		super();
		this.roomtype = roomtype;
		this.floor = floor;
		this.notes = notes;
		this.gender = gender;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoomtype() {
		return roomtype;
	}

	public void setRoomtype(String roomtype) {
		this.roomtype = roomtype;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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
		Room other = (Room) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", roomtype=" + roomtype + ", floor=" + floor + ", gender=" + gender + ", notes="
				+ notes + "]";
	}

}
