package gr.aueb.dmst.simplinvoice.dao;

import gr.aueb.dmst.simplinvoice.model.DocumentTax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentChargesRepository extends JpaRepository<DocumentTax, Long> {

    List<DocumentTax> findAllByDocumentHeaderId(Long documentHeaderId);

}