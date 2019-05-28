package nursing_home.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Worker")
@XmlType(propOrder = { "name", "gender", "job","hire_date", "dob", "salary", "photo" })


public class Worker implements Serializable{

	private static final long serialVersionUID = 7225947562836015154L;
	@XmlAttribute
	private Integer id;
	@XmlAttribute
	private String name;
	@XmlAttribute
	private String gender;
	@XmlAttribute
	private String job;
	@XmlAttribute
	@XmlJavaTypeAdapter(nursing_home.db.xml.utils.SQLDateAdapter.class)
	private Date hire_date;
	@XmlAttribute
	private Date dob;
	@XmlAttribute
	private Double salary;
	@XmlElement
	private byte[] photo;
	
	public Integer getId() {
		return id;
	}			
	
	
	public Worker() {
		super();
	}


	
	public Worker(String name, String gender, String job, Date hire_date, Date dob, Double salary,byte[] photo) {
		super();
		
		this.name = name;
		this.gender=gender;
		this.job = job;
		this.hire_date = hire_date;
		this.dob= dob;
		this.salary = salary;
		this.photo = photo;
	}
	
	public Worker(Integer id,String name,String gender, String job, Date hire_date, Date dob, Double salary, byte[] photo) {
		super();
		
		this.id=id;
		this.name = name;
		this.job = job;
		this.hire_date = hire_date;
		this.dob= dob;
		this.salary = salary;
		this.photo = photo;
	}
	
	@Override
	public String toString() {
		return "Worker [id=" + id + ", name=" + name + ", job=" + job + ", hire_date=" + hire_date + ",dob=" + dob + ", salary="
				+ salary + "]\n"; 
	}
	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String toStringpartial() {
		return "Worker [id=" + id + ", name=" + name+"]\n"; 
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
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Date getHire_date() {
		return hire_date;
	}
	public void setHire_date(Date hire_date) {
		this.hire_date = hire_date;
	}
	
	public Date getDob() {
		return dob;
	}


	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
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
		Worker other = (Worker) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
