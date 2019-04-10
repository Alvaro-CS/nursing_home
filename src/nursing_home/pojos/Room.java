package nursing_home.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity 
@Table(name = "rooms") 
public class Room implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6749324661753403309L;
	
	@Id 
	@GeneratedValue(generator="rooms")
	@TableGenerator(name="rooms", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq", pkColumnValue="rooms")
	
	private Integer id;
	private String roomtype;
	private Integer floor;
	private String gender;
	private String notes;
	@OneToMany(mappedBy="room")
	private List <Resident> residents;

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
	
	public List<Resident> getResidents() {
		return residents;
	}

	public void setResidents(List<Resident> residents) {
		this.residents = residents;
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
