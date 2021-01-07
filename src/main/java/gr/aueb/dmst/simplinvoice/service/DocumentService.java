package gr.aueb.dmst.simplinvoice.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import gr.aueb.dmst.simplinvoice.Utils;
import gr.aueb.dmst.simplinvoice.dao.DocumentHeaderRepository;
import gr.aueb.dmst.simplinvoice.dao.DocumentItemRepository;
import gr.aueb.dmst.simplinvoice.dao.DocumentSeriesRepository;
import gr.aueb.dmst.simplinvoice.dao.DocumentTaxRepository;
import gr.aueb.dmst.simplinvoice.enums.DocumentType;
import gr.aueb.dmst.simplinvoice.model.DocumentHeader;
import gr.aueb.dmst.simplinvoice.model.DocumentItem;
import gr.aueb.dmst.simplinvoice.model.DocumentTax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class DocumentService {

    @Autowired
    DocumentHeaderRepository documentHeaderRepository;

    @Autowired
    DocumentItemRepository documentItemRepository;

    @Autowired
    DocumentTaxRepository documentTaxRepository;

    @Autowired
    DocumentSeriesRepository documentSeriesRepository;

    @Autowired
    DocumentToInvoiceDocConverter documentToInvoiceDocConverter;

    public DocumentHeader getDocumentHeaderById(Long id, Long companyProfileId) {
        return documentHeaderRepository.findDocumentHeaderByIdAndCompanyProfileId(id, companyProfileId);
    }

    public DocumentHeader getDocumentHeaderPublic(String authenticationCode, String requestUrl) throws Exception {
        DocumentHeader documentHeader = documentHeaderRepository.findDocumentHeaderByAuthenticationCode(authenticationCode);

        documentToInvoiceDocConverter.convertDocumentToInvoiceDoc(documentHeader);
        documentToInvoiceDocConverter.retrieveDataFromResponseObject(documentHeader);

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

        if(isNewEntry) {
            sendToMyData(documentHeader);
        }

        return documentHeader;
    }

    void updateDocumentSeriesNumber(DocumentHeader documentHeader) {
        documentHeader.getDocumentSeries().setLastNumber(documentHeader.getNumber());
        documentSeriesRepository.save(documentHeader.getDocumentSeries());
    }

    public void sendToMyData(DocumentHeader documentHeader) {
        documentHeader.setMark(Utils.generateMockMyDataMark());
        documentHeader.setUid(Utils.createMyDataUid(documentHeader));
        documentHeader.setAuthenticationCode(Utils.createMyDataAuthenticationCode(documentHeader));

        documentHeaderRepository.save(documentHeader);
    }

    public BufferedImage generateQRCodeImage(String requestUrl) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(requestUrl, BarcodeFormat.QR_CODE, 200, 200);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

}
