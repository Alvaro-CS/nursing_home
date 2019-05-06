package nursing_home.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@Table (name="residents")

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Resident")
@XmlType(propOrder = {"name", "gender","dob","teleph","dep_grade","checkin","notes" })

public class Resident implements Serializable {
	
	private static final long serialVersionUID = 421019080175306753L;
	@Id
	@GeneratedValue(generator="residents")
	@TableGenerator (name="residents", table= "sqlite_sequence",
		pkColumnName= "name", valueColumnName="seq", pkColumnValue="residents") //Create table
	
	@XmlAttribute
	private Integer id;
	@XmlAttribute
	private String name;
	@XmlAttribute
	private String gender;
	@XmlElement
	@XmlJavaTypeAdapter(nursing_home.db.xml.utils.SQLDateAdapter.class)
	private Date dob;
	@Column(name="telephone")
	@XmlElement
	private Integer teleph;
	@Column(name="grade")
	@XmlElement
	private String dep_grade;
	@XmlElement
	@XmlJavaTypeAdapter(nursing_home.db.xml.utils.SQLDateAdapter.class)
	private Date checkin;
	@Basic(fetch=FetchType.LAZY)
	@Lob
	@XmlTransient
	private byte[] photo;
	@XmlElement
	private String notes;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="room_id")
	@XmlTransient
	private Room room;
	@Transient
	@XmlTransient
	/*
	@XmlElement(name = "Treatment")
    @XmlElementWrapper(name = "Treatments")*/
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
				+ ", dep_grade=" + dep_grade + ", checkin=" + checkin + ", notes="+ notes + ", room: "+room.getId() +"]\n";
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
