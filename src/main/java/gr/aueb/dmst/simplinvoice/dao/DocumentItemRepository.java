package gr.aueb.dmst.simplinvoice.dao;

import gr.aueb.dmst.simplinvoice.dao.projections.DocumentItemByMaterial;
import gr.aueb.dmst.simplinvoice.model.DocumentHeader;
import gr.aueb.dmst.simplinvoice.model.DocumentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentItemRepository extends JpaRepository<DocumentItem, Long> {

    public void deleteAllByDocumentHeader(DocumentHeader documentHeader);

    @Query(value = "SELECT material.description as name, COUNT(item.material_id) as total " +
            "FROM document_item as item INNER JOIN document_header as doc ON (item.document_header_id = doc.id) " +
            "INNER JOIN material ON (item.material_id = material.id) " +
            "WHERE doc.date > now() - INTERVAL ?1 month AND doc.type = ?2 AND doc.company_profile_id = ?3 " +
            "GROUP BY item.material_id", nativeQuery = true)
    public List<DocumentItemByMaterial> getDocumentItemByMaterial(Integer lastMonths, String type, Long companyProfileId);

}