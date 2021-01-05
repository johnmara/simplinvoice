package gr.aueb.dmst.simplinvoice.dao;

import gr.aueb.dmst.simplinvoice.model.DocumentHeader;
import gr.aueb.dmst.simplinvoice.model.DocumentItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentItemRepository extends JpaRepository<DocumentItem, Long> {

    public void deleteAllByDocumentHeader(DocumentHeader documentHeader);

}