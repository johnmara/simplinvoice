package gr.aueb.dmst.simplinvoice.service;

import gr.aueb.dmst.simplinvoice.dao.DocumentHeaderRepository;
import gr.aueb.dmst.simplinvoice.dao.DocumentItemRepository;
import gr.aueb.dmst.simplinvoice.dao.DocumentSeriesRepository;
import gr.aueb.dmst.simplinvoice.dao.DocumentTaxRepository;
import gr.aueb.dmst.simplinvoice.enums.DocumentType;
import gr.aueb.dmst.simplinvoice.model.DocumentHeader;
import gr.aueb.dmst.simplinvoice.model.DocumentItem;
import gr.aueb.dmst.simplinvoice.model.DocumentTax;
import gr.aueb.dmst.simplinvoice.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public DocumentHeader getDocumentHeaderById(Long id, Long companyProfileId) {
        return documentHeaderRepository.findDocumentHeaderByIdAndCompanyProfileId(id, companyProfileId);
    }

    public List<DocumentHeader> getDocumentsList(DocumentType documentType, Long companyProfileId) {
        return documentHeaderRepository.findAllByTypeAndCompanyProfileId(documentType, companyProfileId);
    }

    @Transactional
    public DocumentHeader addDocumentHeader(DocumentHeader documentHeader) {
        updateDocumentSeriesNumber(documentHeader);
        documentHeader.setReceivedDateTime(new Date());
        if(documentHeader.getId() != null) { //update
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

        return documentHeader;
    }

    void updateDocumentSeriesNumber(DocumentHeader documentHeader) {
        documentHeader.getDocumentSeries().setLastNumber(documentHeader.getNumber());
        documentSeriesRepository.save(documentHeader.getDocumentSeries());
    }

}
