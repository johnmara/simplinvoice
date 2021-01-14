package gr.aueb.dmst.simplinvoice.dao;

import gr.aueb.dmst.simplinvoice.dao.projections.DocumentHeaderByCounterpart;
import gr.aueb.dmst.simplinvoice.dao.projections.DocumentHeaderByMonthSum;
import gr.aueb.dmst.simplinvoice.dao.projections.DocumentHeaderTotal;
import gr.aueb.dmst.simplinvoice.enums.DocumentType;
import gr.aueb.dmst.simplinvoice.model.DocumentHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentHeaderRepository extends JpaRepository<DocumentHeader, Long> {

    List<DocumentHeader> findAllByTypeAndCompanyProfileId(DocumentType documentType, Long companyProfileId);

    List<DocumentHeader> findAllByTypeAndCompanyProfileIdAndMarkIsNull(DocumentType documentType, Long companyProfileId);

    DocumentHeader findDocumentHeaderByIdAndCompanyProfileId(Long id, Long companyProfileId);

    DocumentHeader findDocumentHeaderById(Long id);

    DocumentHeader findDocumentHeaderByAuthenticationCode(String authenticationCode);

    @Query(value = "SELECT YEAR(doc.date) as year, MONTH(doc.date) as month, SUM(doc.total_net_value) as sum " +
            "FROM document_header as doc " +
            "WHERE doc.date > now() - INTERVAL ?1 month AND doc.type = ?2 AND doc.company_profile_id = ?3 " +
            "GROUP BY YEAR(doc.date), MONTH(doc.date) ORDER BY YEAR(doc.date) DESC, MONTH(doc.date) DESC", nativeQuery = true)
    public List<DocumentHeaderByMonthSum> getSumInvoicesByMonth(Integer lastMonths, String type, Long companyProfileId);

    @Query(value = "SELECT SUM(doc.total_net_value) as sum " +
            "FROM document_header as doc " +
            "WHERE doc.date > now() - INTERVAL ?1 month AND doc.type = ?2 AND doc.company_profile_id = ?3", nativeQuery = true)
    public DocumentHeaderTotal getDocumentTotalValue(Integer lastMonths, String type, Long companyProfileId);

    @Query(value = "SELECT trader.name as name, COUNT(doc.trader_id) as total " +
            "FROM document_header as doc INNER JOIN trader ON (doc.trader_id = trader.id)" +
            "WHERE doc.date > now() - INTERVAL ?1 month AND doc.type = ?2 AND doc.company_profile_id = ?3 " +
            "GROUP BY doc.trader_id", nativeQuery = true)
    public List<DocumentHeaderByCounterpart> getDocumentByCounterpart(Integer lastMonths, String type, Long companyProfileId);

}