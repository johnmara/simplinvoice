package gr.aueb.dmst.simplinvoice;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlUtils {

    public static String marshal(Object requestedObject) throws JAXBException, IOException {

        JAXBContext context = JAXBContext.newInstance(requestedObject.getClass());
        Marshaller mar= context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);


        StringWriter sw = new StringWriter();
        mar.marshal(requestedObject, sw);

        return sw.toString();
    }

    public static <T> T unmarshall(String textToParse, Class<T> classType) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(classType);
        StringReader reader = new StringReader(textToParse);
        return (T) context.createUnmarshaller().unmarshal(reader);
    }

}
