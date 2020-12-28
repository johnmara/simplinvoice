package gr.aueb.dmst.simplinvoice.repository;

import gr.aueb.dmst.simplinvoice.dao.DeprecatedProductRepository;
import gr.aueb.dmst.simplinvoice.model.DeprecatedProduct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@DataJpaTest
public class DeprecatedDeprecatedProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    DeprecatedProductRepository repository;

    @Test
    public void returnAllProducts() {
        // given
        DeprecatedProduct deprecatedProduct1 = new DeprecatedProduct("Ball");
        DeprecatedProduct deprecatedProduct2 = new DeprecatedProduct("Table");
        entityManager.persist(deprecatedProduct1);
        entityManager.persist(deprecatedProduct2);
        entityManager.flush();

        // when
        List<DeprecatedProduct> found = (List<DeprecatedProduct>) repository.findAll();

        // then
        Assertions.assertEquals(2, found.size());
    }

    @Test
    public void addProduct() {
        // given
        DeprecatedProduct deprecatedProduct = new DeprecatedProduct("Ball");
        Long id = repository.save(deprecatedProduct).getId();

        // when
        DeprecatedProduct found = repository.findById(id).orElse(null);

        // then
        Assertions.assertEquals("Ball", found.getName());
    }

}
