package gr.aueb.dmst.simplinvoice.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import generated.ResponseDoc;
import gr.aueb.dmst.simplinvoice.Utils;
import gr.aueb.dmst.simplinvoice.client.MyDataRestClient;
import gr.aueb.dmst.simplinvoice.dao.DocumentHeaderRepository;
import gr.aueb.dmst.simplinvoice.dao.DocumentItemRepository;
import gr.aueb.dmst.simplinvoice.dao.DocumentSeriesRepository;
import gr.aueb.dmst.simplinvoice.dao.DocumentTaxRepository;
import gr.aueb.dmst.simplinvoice.enums.DocumentType;
import gr.aueb.dmst.simplinvoice.exception.MydataValidationException;
import gr.aueb.dmst.simplinvoice.model.DocumentHeader;
import gr.aueb.dmst.simplinvoice.model.DocumentItem;
import gr.aueb.dmst.simplinvoice.model.DocumentTax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.xml.sax.SAXException;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    Logger logger = LoggerFactory.getLogger(DocumentService.class);

    @Autowired
    DocumentHeaderRepository documentHeaderRepository;

    @Autowired
    DocumentItemRepository documentItemRepository;

    @Autowired
    DocumentTaxRepository documentTaxRepository;

    @Autowired
    DocumentSeriesRepository documentSeriesRepository;

    @Autowired
    MyDataConverterService myDataConverterService;

    @Autowired
    MyDataRestClient myDataRestClient;

    public DocumentHeader getDocumentHeaderById(Long id, Long companyProfileId) {
        DocumentHeader documentHeader = documentHeaderRepository.findDocumentHeaderById(id);
        return documentHeaderRepository.findDocumentHeaderByIdAndCompanyProfileId(id, companyProfileId);
    }

    public DocumentHeader getDocumentHeaderPublic(String authenticationCode, String requestUrl) throws Exception {
        DocumentHeader documentHeader = documentHeaderRepository.findDocumentHeaderByAuthenticationCode(authenticationCode);

        BufferedImage bufferedImage = generateQRCodeImage(requestUrl);

        String bytesBase64 = Base64.getEncoder().encodeToString(Utils.toByteArray(bufferedImage, "png"));
        documentHeader.setQrCodeValue(bytesBase64);

        return documentHeader;
    }

    public List<DocumentHeader> getDocumentsList(DocumentType documentType, Long companyProfileId) {
        return documentHeaderRepository.findAllByTypeAndCompanyProfileId(documentType, companyProfileId);
    }

    @Transactional
    public DocumentHeader addDocumentHeader(DocumentHeader documentHeader) {
        boolean isNewEntry = documentHeader.getId() == null;

        if(documentHeader.getType().equals(DocumentType.ISSUED))
            updateDocumentSeriesNumber(documentHeader);

        documentHeader.setReceivedDateTime(new Date());
        if(!isNewEntry) {
            documentItemRepository.deleteAllByDocumentHeader(documentHeader);
            documentTaxRepository.deleteAllByDocumentHeaderId(documentHeader.getId());
        }

        documentHeader = documentHeaderRepository.save(documentHeader);

        for(DocumentItem item: documentHeader.getDocumentItems()) {
            item.setDocumentHeader(documentHeader);
            documentItemRepository.save(item);
        }
        if (documentHeader.getDocumentTaxes() != null)
            for(DocumentTax tax: documentHeader.getDocumentTaxes()) {
                tax.setDocumentHeader(documentHeader);
                documentTaxRepository.save(tax);
            }

        if(isNewEntry && documentHeader.getType().equals(DocumentType.ISSUED)) {
            sendToMyData(Collections.singletonList(documentHeader), documentHeader.getCompanyProfile().getId());
        }

        return documentHeader;
    }

    void updateDocumentSeriesNumber(DocumentHeader documentHeader) {
        documentHeader.getDocumentSeries().setLastNumber(documentHeader.getNumber());
        documentSeriesRepository.save(documentHeader.getDocumentSeries());
    }

    public void forwardUnsentToMydata(Long companyProfileId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<DocumentHeader> unsentDocuments = documentHeaderRepository.
                        findAllByTypeAndCompanyProfileIdAndMarkIsNull(DocumentType.ISSUED, companyProfileId);

                sendToMyData(unsentDocuments, companyProfileId);
            }
        }).start();
    }

    public void sendToMyData(List<DocumentHeader> documentHeaderList, Long companyProfileId) {

        if(documentHeaderList.size() == 0)
            return;

        String invoicesXml;
        ResponseDoc responseDoc;

        try {
            invoicesXml = myDataConverterService.convertDocumentsToXml(documentHeaderList);
            responseDoc = myDataRestClient.sendInvoiceDoc(invoicesXml, companyProfileId);
        } catch (JAXBException | IOException | SAXException ex) {
            logger.error("Error during constructing xml for myData");
            return;
        } catch (URISyntaxException | HttpStatusCodeException ex) {
            logger.error("Error during sending xml to myData");
            return;
        } catch (MydataValidationException ex) {
            logger.error("Error during validation xml for myData", ex);
            return;
        }

        responseDoc.getResponse().forEach(responseType -> {
            int index = responseType.getIndex() - 1;
            DocumentHeader documentHeader = documentHeaderList.get(index);

            if(responseType.getErrors() == null) {
                documentHeader.setMark(String.valueOf(responseType.getInvoiceMark()));
                documentHeader.setUid(responseType.getInvoiceUid());
                documentHeader.setAuthenticationCode(responseType.getAuthenticationCode());

            } else {
                List<String> documentErrors = responseType.getErrors().getError().stream().map(f -> f.getCode() + " - " + f.getMessage())
                        .collect(Collectors.toList());
                documentHeader.setMyDataErrorsList(documentErrors);
            }

            documentHeaderRepository.save(documentHeader);
        });


    }

    public BufferedImage generateQRCodeImage(String requestUrl) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(requestUrl, BarcodeFormat.QR_CODE, 200, 200);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

}
