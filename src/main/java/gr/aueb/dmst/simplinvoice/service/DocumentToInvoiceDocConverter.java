package gr.aueb.dmst.simplinvoice.service;

import generated.ResponseDoc;
import gr.aade.mydata.invoice.v1.*;
import gr.aueb.dmst.simplinvoice.XmlUtils;
import gr.aueb.dmst.simplinvoice.model.DocumentHeader;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class DocumentToInvoiceDocConverter {

    public String convertDocumentToInvoiceDoc(DocumentHeader documentHeader) throws JAXBException, IOException {

        InvoicesDoc invoicesDoc = new InvoicesDoc();
        invoicesDoc.getInvoice().add(new AadeBookInvoiceType());

        //Counterpart
        PartyType counterpart = new PartyType();
        counterpart.setVatNumber(documentHeader.getCounterPart().getAfm());
        counterpart.setCountry(CountryType.valueOf(documentHeader.getCounterPart().getCountry()));
        counterpart.setBranch(documentHeader.getCounterPart().getBranch());
        counterpart.setName(documentHeader.getCounterPart().getName());
        counterpart.setAddress(new AddressType());
        counterpart.getAddress().setStreet(documentHeader.getCounterPart().getStreet());
        counterpart.getAddress().setNumber(String.valueOf(documentHeader.getCounterPart().getStreetNumber()));
        counterpart.getAddress().setPostalCode(documentHeader.getCounterPart().getPostalCode());
        counterpart.getAddress().setCity(documentHeader.getCounterPart().getTown());

        invoicesDoc.getInvoice().get(0).setCounterpart(counterpart);

        //Counterpart
        PartyType issuer = new PartyType();
        issuer.setVatNumber(documentHeader.getCompanyProfile().getAfm());
        issuer.setCountry(CountryType.valueOf(documentHeader.getCompanyProfile().getCountry()));
        issuer.setBranch(documentHeader.getCompanyProfile().getBranch());
        issuer.setName(documentHeader.getCompanyProfile().getCompanyName());
        issuer.setAddress(new AddressType());
        issuer.getAddress().setStreet(documentHeader.getCompanyProfile().getStreet());
        issuer.getAddress().setNumber(String.valueOf(documentHeader.getCompanyProfile().getStreetNumber()));
        issuer.getAddress().setPostalCode(documentHeader.getCompanyProfile().getPostalCode());
        issuer.getAddress().setCity(documentHeader.getCompanyProfile().getTown());

        invoicesDoc.getInvoice().get(0).setCounterpart(issuer);

        String generatedXml = XmlUtils.marshal(invoicesDoc);

        return generatedXml;
    }

    public DocumentHeader retrieveDataFromResponseObject(DocumentHeader documentHeader) throws JAXBException, IOException {
        String response = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<ResponseDoc xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "  <response>\n" +
                "    <index>1</index>\n" +
                "    <invoiceUid>9078A518B1E197DC645C44DDF8C286A58C1CCBE0</invoiceUid>\n" +
                "    <invoiceMark>400001829527564</invoiceMark>\n" +
                "    <authenticationCode>1E7D139EF4C0D34415094D3DF57F6670D783662D</authenticationCode>\n" +
                "    <statusCode>Success</statusCode>\n" +
                "  </response>\n" +
                "</ResponseDoc>";

        ResponseDoc responseDoc = XmlUtils.unmarshall(response, ResponseDoc.class);

        return documentHeader;
    }

}
