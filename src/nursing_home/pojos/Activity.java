package nursing_home.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Activity")
@XmlType(propOrder = {"name","hours","days","location"})

public class Activity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6896487450338046523L;
	@XmlAttribute
	private Integer id;
	@XmlAttribute
	private String name;
	@XmlAttribute
	private String hours;
	@XmlAttribute
	private String days;
	@XmlAttribute
	private String location;

	public Activity() {
		super();
	}

	public Activity(String name, String hours, String days, String location) {
		super();
		this.name = name;
		this.hours = hours;
		this.days = days;
		this.location = location;
	}

	public Activity(Integer id, String name, String hours, String days, String location) {
		super();
		this.id = id;
		this.name = name;
		this.hours = hours;
		this.days = days;
		this.location = location;
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

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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
		Activity other = (Activity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Activity [id=" + id + ", name=" + name + ", hours=" + hours + ", days=" + days + ", location="
				+ location + "]";
	}

	public String toStringpartial() {
		return "Activity [id=" + id + ", name=" + name +"]";
	}
	
}
