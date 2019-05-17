package nursing_home.pojos;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Activity_list")
@XmlType(propOrder = {"activities" })

public class Activity_list {
	@XmlElement(name="Activity")
	private List<Activity> activities;


	@Override
	public String toString() {
		return "Activity_list [activities=" + activities + "]";
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public Activity_list(List<Activity> activities) {
		super();
		this.activities = activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public Activity_list() {
		super();
	}
	
}
