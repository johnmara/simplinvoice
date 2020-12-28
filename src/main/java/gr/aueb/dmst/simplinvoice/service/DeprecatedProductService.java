package gr.aueb.dmst.simplinvoice.service;

import gr.aueb.dmst.simplinvoice.dao.DeprecatedProductRepository;
import gr.aueb.dmst.simplinvoice.dao.specifications.GenericSpecification;
import gr.aueb.dmst.simplinvoice.dao.specifications.SearchCriteria;
import gr.aueb.dmst.simplinvoice.model.DeprecatedProduct;
import gr.aueb.dmst.simplinvoice.model.request.DeprecatedProductPageListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeprecatedProductService {

    @Autowired
    DeprecatedProductRepository deprecatedProductRepository;

    public DeprecatedProduct addProduct(DeprecatedProduct deprecatedProduct) {
        return deprecatedProductRepository.save(deprecatedProduct);
    }

    public List<DeprecatedProduct> getProductsList() {
        return (List<DeprecatedProduct>) deprecatedProductRepository.findAll();
    }

    public Page<DeprecatedProduct> findPaginatedJpa(DeprecatedProductPageListRequest deprecatedProductPageListRequest, Pageable pageable) {
        GenericSpecification<DeprecatedProduct> spec = new GenericSpecification<DeprecatedProduct>(new SearchCriteria("name", ":", deprecatedProductPageListRequest.getName()));
        GenericSpecification<DeprecatedProduct> spec2 = new GenericSpecification<DeprecatedProduct>(new SearchCriteria("category", ":", deprecatedProductPageListRequest.getCategory()));
        return deprecatedProductRepository.findAll(spec.and(spec2), pageable);
    }

    public DeprecatedProduct getProductById(Long id) {
        return deprecatedProductRepository.findById(id.longValue());
    }

}
