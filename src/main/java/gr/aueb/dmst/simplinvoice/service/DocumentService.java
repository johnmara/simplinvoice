package gr.aueb.dmst.simplinvoice.service;

import gr.aueb.dmst.simplinvoice.dao.DocumentHeaderRepository;
import gr.aueb.dmst.simplinvoice.enums.DocumentType;
import gr.aueb.dmst.simplinvoice.model.DocumentHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    @Autowired
    DocumentHeaderRepository documentHeaderRepository;

    public List<DocumentHeader> getDocumentsList(DocumentType documentType, Long companyProfileId) {
        return documentHeaderRepository.findAllByTypeAndCompanyProfileId(documentType, companyProfileId);
    }

    public DocumentHeader addDocumentHeader(DocumentHeader documentHeader) {
        return documentHeaderRepository.save(documentHeader);
    }

}
