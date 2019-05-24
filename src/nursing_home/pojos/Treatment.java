package nursing_home.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Treatment")
@XmlType(propOrder = {"name", "initial_date","final_date"})

public class Treatment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7466068630410510066L;
	@XmlAttribute
	private Integer id;
	@XmlAttribute
	private String name;
	@XmlAttribute
	@XmlJavaTypeAdapter(nursing_home.db.xml.utils.SQLDateAdapter.class)
	private Date initial_date;
	@XmlAttribute
	@XmlJavaTypeAdapter(nursing_home.db.xml.utils.SQLDateAdapter.class)
	private Date final_date;
	@XmlTransient
	private Resident resident;

	
	public Treatment(Integer id, String name, Date initial_date, Date final_date, Resident resident) {
		super();
		this.id = id;
		this.name = name;
		this.initial_date = initial_date;
		this.final_date = final_date;
		this.resident = resident;
	}

	public Treatment() {
		super();
	}

	public Treatment(String name, Date initial_date, Date final_date, Resident resident) {
		super();
		this.name = name;
		this.initial_date = initial_date;
		this.final_date = final_date;
		this.resident = resident;
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

	public Date getInitial_date() {
		return initial_date;
	}

	public void setInitial_date(Date initial_date) {
		this.initial_date = initial_date;
	}

	public Date getFinal_date() {
		return final_date;
	}

	public void setFinal_date(Date final_date) {
		this.final_date = final_date;
	}

	public Resident getResident() {
		return resident;
	}

	public void setResident(Resident resident) {
		this.resident = resident;
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
		Treatment other = (Treatment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Treatment [id=" + id + ", name=" + name + ", initial_date=" + initial_date + ", final_date="
				+ final_date + ", resident:"+resident.getName()+"]";
	}
	public String toStringpartial() {
		return "Treatment [id=" + id + ", name=" + name +", Resident:"+resident.getName() +"]";
	}

}
