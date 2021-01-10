package gr.aueb.dmst.simplinvoice;

import gr.aueb.dmst.simplinvoice.enums.MydataEntitiesEnum;
import gr.aueb.dmst.simplinvoice.exception.MydataValidationException;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlUtils {

    public static String convertToxml(Object requestedObject) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(requestedObject.getClass());
        Marshaller mar= context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter sw = new StringWriter();
        mar.marshal(requestedObject, sw);

        return sw.toString();
    }

    public static <T> T parseXml(String textToParse, MydataEntitiesEnum myDataDoc) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(myDataDoc.classType);
        StringReader reader = new StringReader(textToParse);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        return (T) jaxbUnmarshaller.unmarshal(reader);


    }

    public static void validate(String textToParse, MydataEntitiesEnum myDataDoc) throws JAXBException, SAXException, IOException, MydataValidationException {
        JAXBContext jaxbContext = JAXBContext.newInstance(myDataDoc.classType);
        StringReader reader = new StringReader(textToParse);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema validationSchema = sf.newSchema(new ClassPathResource(myDataDoc.xsdPath).getFile());
        jaxbUnmarshaller.setSchema(validationSchema);

        try {
            jaxbUnmarshaller.unmarshal(reader);
        } catch (JAXBException ex) {
            String errorMessage = ex.getLinkedException().getMessage();

            throw new MydataValidationException(errorMessage.substring(errorMessage.indexOf(":") + 2));
        }

    }

}
