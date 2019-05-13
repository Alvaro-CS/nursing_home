package nursing_home.db.xml;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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
	public Room_list unmarshallRooms(String name) throws JAXBException {
		

		JAXBContext jaxbContext = JAXBContext.newInstance(Room_list.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		File file = new File("./xmls/"+name);
		Room_list rooms = (Room_list) unmarshaller.unmarshal(file);
		return rooms;
	}
//TODO xlst
}
