package nursing_home.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table (name="residents")
public class Resident implements Serializable {
	
	private static final long serialVersionUID = 421019080175306753L;
	@Id
	@GeneratedValue(generator="residents")
	@TableGenerator (name="residents", table= "sqlite_sequence",
		pkColumnName= "name", valueColumnName="seq", pkColumnValue="residents") //Create table
	
	//serial version
	private Integer id;
	private String name;
	private String gender;
	private Date dob;
	private Integer teleph;
	private String dep_grade;
	private Date checkin;
	@Basic(fetch=FetchType.LAZY)
	@Lob
	private byte[] photo;
	private String notes;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="room_id")
	private Room room;
	private List <Treatment> treatments;
	
	
	public Resident() {
		super();
		this.treatments= new ArrayList<Treatment>();
	}
	


	public Resident(String name, String gender, Date dob, Integer teleph, String dep_grade, Date checkin, byte[] photo,
			String notes, Room room) {
		super();
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		this.teleph = teleph;
		this.dep_grade = dep_grade;
		this.checkin = checkin;
		this.photo = photo;
		this.notes = notes;
		this.room = room;
	}



	public Resident(Integer id, String name, String gender, Date dob, Integer teleph, String dep_grade, Date checkin,
			byte[] photo, String notes, Room r) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		this.teleph = teleph;
		this.dep_grade = dep_grade;
		this.checkin = checkin;
		this.photo = photo;
		this.notes = notes;
		this.room = r;

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

	public String getDep_grade() {
		return dep_grade;
	}

	public void setDep_grade(String dep_grade) {
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
	
	public Room getRoom() {
		return room;
	}


	public void setRoom(Room room) {
		this.room = room;
	}

	public List<Treatment> getTreatments() {
		return treatments;
	}


	public void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}


	@Override
	public String toString() {
		return "Resident [id=" + id + ", name=" + name + ", gender=" + gender + ", dob=" + dob + ", teleph=" + teleph
				+ ", dep_grade=" + dep_grade + ", checkin=" + checkin + ", photo=" + Arrays.toString(photo) + ", notes="
				+ notes + "]\n";
	}
	
	public String toStringpartial() {
		return "Resident [id=" + id + ", name=" + name+"]\n"; 
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
