package gr.aueb.dmst.simplinvoice.controller.api;

import generated.ErrorType;
import generated.ResponseDoc;
import generated.ResponseType;
import gr.aade.mydata.invoice.v1.InvoicesDoc;
import gr.aueb.dmst.simplinvoice.Utils;
import gr.aueb.dmst.simplinvoice.XmlUtils;
import gr.aueb.dmst.simplinvoice.enums.MydataEntitiesEnum;
import gr.aueb.dmst.simplinvoice.exception.MydataValidationException;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.UUID;
import java.util.stream.IntStream;

@RestController
@RequestMapping(value = "/mock/mydata")
public class MockMyDataController {

    @PostMapping("/myDATAProvider/SendInvoices")
    public String sendInvoices(@RequestBody String invoicesDocXml, @RequestParam(defaultValue = "false") boolean error) throws JAXBException, IOException, SAXException, MydataValidationException {

        ResponseDoc responseDoc = new ResponseDoc();

        InvoicesDoc invoicesDoc = XmlUtils.parseXml(invoicesDocXml, MydataEntitiesEnum.INVOICES_DOC);

        IntStream.range(0, invoicesDoc.getInvoice().size()).forEach(idx -> {
            ResponseType responseType = new ResponseType();
            responseType.setIndex(idx + 1);

            if(error) {
                responseType.setErrors(new ResponseType.Errors());

                ErrorType errorType = new ErrorType();
                errorType.setCode(1);
                errorType.setMessage("An error occured during validation");
                responseType.getErrors().getError().add(errorType);

                responseType.setStatusCode("ValidationError");
            } else {
                responseType.setInvoiceUid(Utils.convertToSha1(UUID.randomUUID().toString()));
                responseType.setInvoiceMark(Long.parseLong(Utils.generateMockMyDataMark()));
                responseType.setAuthenticationCode(Utils.convertToSha1(UUID.randomUUID().toString()));
                responseType.setStatusCode("Success");
            }

            responseDoc.getResponse().add(responseType);
        });

        return XmlUtils.convertToxml(responseDoc);
    }

}
