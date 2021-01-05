package gr.aueb.dmst.simplinvoice.dao;

import gr.aueb.dmst.simplinvoice.enums.DocumentType;
import gr.aueb.dmst.simplinvoice.model.DocumentHeader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentHeaderRepository extends JpaRepository<DocumentHeader, Long> {

    List<DocumentHeader> findAllByTypeAndCompanyProfileId(DocumentType documentType, Long companyProfileId);

    DocumentHeader findDocumentHeaderByIdAndCompanyProfileId(Long id, Long companyProfileId);


}