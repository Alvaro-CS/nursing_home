package nursing_home.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Resident implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 421019080175306753L;
	//serial version
	private Integer id;
	private String name;
	private String gender;
	private Date dob;
	private Integer teleph;
	private Integer dep_grade;
	private Date checkin;
	private byte[] photo;
	private String notes;
	private List<Room> rooms;
	
	public Resident() {
		super();
		this.rooms = new ArrayList<Room>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Integer getTeleph() {
		return teleph;
	}

	public void setTeleph(Integer teleph) {
		this.teleph = teleph;
	}

	public Integer getDep_grade() {
		return dep_grade;
	}

	public void setDep_grade(Integer dep_grade) {
		this.dep_grade = dep_grade;
	}

	public Date getCheckin() {
		return checkin;
	}

	public void setCheckin(Date checkin) {
		this.checkin = checkin;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	@Override
	public String toString() {
		return "Resident [id=" + id + ", name=" + name + ", gender=" + gender + ", dob=" + dob + ", teleph=" + teleph
				+ ", dep_grade=" + dep_grade + ", checkin=" + checkin + ", photo=" + Arrays.toString(photo) + ", notes="
				+ notes + "]";
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
		Resident other = (Resident) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}
