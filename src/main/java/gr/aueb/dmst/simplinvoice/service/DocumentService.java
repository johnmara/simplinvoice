package gr.aueb.dmst.simplinvoice.service;

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

    public List<DocumentHeader> getDocumentsList(DocumentType documentType, Long companyProfileId) {
        return documentHeaderRepository.findAllByTypeAndCompanyProfileId(documentType, companyProfileId);
    }

    public DocumentHeader addDocumentHeader(DocumentHeader documentHeader) {
        documentHeader.getDocumentSeries().setLastNumber(documentHeader.getNumber());
        documentSeriesRepository.save(documentHeader.getDocumentSeries());
        documentHeader.setReceivedDateTime(new Date());
        documentHeader = documentHeaderRepository.save(documentHeader);

        for(DocumentItem item: documentHeader.getDocumentItems()) {
            item.setDocumentHeader(documentHeader);
            documentItemRepository.save(item);
        }
        for(DocumentTax tax: documentHeader.getDocumentTaxes()) {
            tax.setDocumentHeader(documentHeader);
            documentTaxRepository.save(tax);
        }

        return documentHeaderRepository.save(documentHeader);
    }

}
