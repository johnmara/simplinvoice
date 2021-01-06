package gr.aueb.dmst.simplinvoice.controller.api;

import generated.ResponseDoc;
import generated.ResponseType;
import gr.aueb.dmst.simplinvoice.Utils;
import gr.aueb.dmst.simplinvoice.XmlUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(value = "/mock/mydata")
public class MockMyDataController {

    @PostMapping("/myDATAProvider/SendInvoices")
    public String sendInvoices(@RequestBody String invoicesDoc) throws JAXBException, IOException {

        ResponseDoc responseDoc = new ResponseDoc();
        ResponseType responseType = new ResponseType();

        responseType.setIndex(1);
        responseType.setInvoiceUid(Utils.convertToSha1(UUID.randomUUID().toString()));
        responseType.setInvoiceMark(Long.parseLong(Utils.generateMockMyDataMark()));
        responseType.setAuthenticationCode(Utils.convertToSha1(UUID.randomUUID().toString()));
        responseType.setStatusCode("Success");

        responseDoc.getResponse().add(responseType);

        return XmlUtils.marshal(responseDoc);
    }


}
