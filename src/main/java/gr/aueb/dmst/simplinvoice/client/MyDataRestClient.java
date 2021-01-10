package gr.aueb.dmst.simplinvoice.client;

import generated.ResponseDoc;
import gr.aueb.dmst.simplinvoice.dao.SystemConfigRepository;
import gr.aueb.dmst.simplinvoice.model.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class MyDataRestClient {

    Logger logger = LoggerFactory.getLogger(MyDataRestClient.class);

    private static final String USER_ID_HEADER_NAME = "aade-user-id";
    private static final String API_KEY_HEADER_NAME = "Ocp-Apim-Subscription-Key";
    private static final String PROVIDER_SEND_INVOICES_PATH = "/myDATAProvider/SendInvoices";

    @Autowired
    SystemConfigRepository systemConfigRepository;

    @Value("${aade.mydata.url}")
    String myDataUrl;

    RestTemplate restTemplate = new RestTemplate();

    public ResponseDoc sendInvoiceDoc(String invoiceDocXml, Long companyProfileId) throws URISyntaxException {
        SystemConfig systemConfig = systemConfigRepository.findSystemConfigByCompanyProfileId(companyProfileId);
        if(systemConfig == null)
            return null;

        String myDataUsername = systemConfig.getMyDataUsername();
        String myDataApiKey = systemConfig.getMyDataApiKey();
        if(myDataUsername == null || myDataApiKey == null)
            return null;

        URI uri = new URI(myDataUrl + PROVIDER_SEND_INVOICES_PATH);
//        URI uri = new URI(myDataUrl + PROVIDER_SEND_INVOICES_PATH + "?error=true");
        HttpHeaders headers = new HttpHeaders();
        headers.set(USER_ID_HEADER_NAME, myDataUsername);
        headers.set(API_KEY_HEADER_NAME, myDataApiKey);

        HttpEntity<String> requestEntity = new HttpEntity<>(invoiceDocXml, headers);

        try {
            return restTemplate.postForEntity(uri, requestEntity, ResponseDoc.class).getBody();
        } catch (HttpStatusCodeException ex) {
            logger.error("Error trying to send Invoice to MyData", ex);
            throw ex;
        }

    }

}
