package gr.aueb.dmst.simplinvoice.service;

import gr.aueb.dmst.simplinvoice.dao.DocumentSeriesRepository;
import gr.aueb.dmst.simplinvoice.model.DocumentSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DocumentSeriesService {

    @Autowired
    DocumentSeriesRepository documentSeriesRepository;

    public List<DocumentSeries> getDocumentSeriesList(Long companyProfileId) {
        return documentSeriesRepository.findAllByCompanyProfileId(companyProfileId);
    }

    public DocumentSeries getDocumentSeriesById(Long id, Long companyProfileId) {
        return documentSeriesRepository.findDocumentSeriesByIdAndCompanyProfileId(id, companyProfileId);
    }

    @Transactional
    public DocumentSeries addDocumentSeries(DocumentSeries documentSeries) {
        return documentSeriesRepository.save(documentSeries);
    }

    @Transactional
    public void deleteDocumentSeries(Long id, Long companyProfileId) {
        documentSeriesRepository.deleteDocumentSeriesByIdAndCompanyProfileId(id, companyProfileId);
    }

}
