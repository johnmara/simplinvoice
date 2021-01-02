package gr.aueb.dmst.simplinvoice.dao;

import gr.aueb.dmst.simplinvoice.model.DocumentSeries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentSeriesRepository extends JpaRepository<DocumentSeries, Long> {

    DocumentSeries findDocumentSeriesByIdAndCompanyProfileId(Long id, Long companyProfileId);

    void deleteDocumentSeriesByIdAndCompanyProfileId(Long id, Long companyProfileId);

    List<DocumentSeries> findAllByCompanyProfileId(Long companyProfileId);

}