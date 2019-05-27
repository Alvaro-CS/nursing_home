package nursing_home.db.xml;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import nursing_home.pojos.*;

public class XMLManager {

	public XMLManager() {
		super();
	}
	
	public void marshallRooms(Room_list rooms,String name) throws JAXBException {
			JAXBContext jaxbContext = JAXBContext.newInstance(Room_list.class);
			Marshaller marshaller = jaxbContext.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			File file = new File("./xmls/"+name);
			marshaller.marshal(rooms, file);
	}
	public void marshallActivities(Activity_list activities,String name) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Activity_list.class);
		Marshaller marshaller = jaxbContext.createMarshaller();

		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		File file = new File("./xmls/"+name);
		marshaller.marshal(activities, file);
}
	public Room_list unmarshallRooms(String name) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Room_list.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		File file = new File("./xmls/"+name);
		Room_list rooms = (Room_list) unmarshaller.unmarshal(file);
		return rooms;
	}
	public Activity_list unmarshallActivities(String name) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Activity_list.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		File file = new File("./xmls/"+name);
		Activity_list activities = (Activity_list) unmarshaller.unmarshal(file);
		return activities;
	}
	
	public simpleTransform(String sourcePath, String xsltPath,String resultDir) {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = tFactory.newTransformer(new StreamSource(new File(xsltPath)));
			transformer.transform(new StreamSource(new File(sourcePath)),new StreamResult(new File(resultDir)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
