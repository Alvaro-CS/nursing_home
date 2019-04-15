package nursing_home.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

public class Worker implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 7225947562836015154L;
	//serial version
	private Integer id;
	private String name;
	private String gender;
	private String job;
	private Date hire_date;
	private Date dob;
	private Double salary;
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
	
	public Worker(Integer id,String name, String job, Date hire_date, Date dob, Double salary, byte[] photo) {
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
				+ salary + "]"; 
	}
	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String toStringpartial() {
		return "Worker [id=" + id + ", name=" + name+"]"; 
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
