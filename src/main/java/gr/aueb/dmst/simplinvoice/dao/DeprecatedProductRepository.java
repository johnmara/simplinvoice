package gr.aueb.dmst.simplinvoice.dao;

import gr.aueb.dmst.simplinvoice.model.DeprecatedProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeprecatedProductRepository extends PagingAndSortingRepository<DeprecatedProduct, Long>, JpaSpecificationExecutor<DeprecatedProduct> {

    Page<DeprecatedProduct> findAll(Specification<DeprecatedProduct> spec, Pageable pageable);

    DeprecatedProduct findById(long id);

}