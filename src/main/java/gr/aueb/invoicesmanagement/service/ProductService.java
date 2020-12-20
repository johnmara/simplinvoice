package gr.aueb.invoicesmanagement.service;

import gr.aueb.invoicesmanagement.dao.ProductRepository;
import gr.aueb.invoicesmanagement.dao.specifications.GenericSpecification;
import gr.aueb.invoicesmanagement.dao.specifications.SearchCriteria;
import gr.aueb.invoicesmanagement.model.Product;
import gr.aueb.invoicesmanagement.model.request.ProductPageListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getProductsList() {
        return (List<Product>) productRepository.findAll();
    }

    public Page<Product> findPaginatedJpa(ProductPageListRequest productPageListRequest, Pageable pageable) {
        GenericSpecification<Product> spec = new GenericSpecification<Product>(new SearchCriteria("name", ":", productPageListRequest.getName()));
        GenericSpecification<Product> spec2 = new GenericSpecification<Product>(new SearchCriteria("category", ":", productPageListRequest.getCategory()));
        return productRepository.findAll(spec.and(spec2), pageable);
    }

}
