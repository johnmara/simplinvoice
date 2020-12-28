package gr.aueb.invoicesmanagement.service;

import gr.aueb.invoicesmanagement.dao.DeprecatedProductRepository;
import gr.aueb.invoicesmanagement.model.DeprecatedProduct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class DeprecatedDeprecatedProductServiceTest {

    @TestConfiguration
    static class DeprecatedProductServiceTestContextConfiguration {

        @Bean
        public DeprecatedProductService productService() {
            return new DeprecatedProductService();
        }

    }

    @Autowired
    private DeprecatedProductService deprecatedProductService;

    @MockBean
    private DeprecatedProductRepository deprecatedProductRepository;

    @BeforeEach
    public void setUp() {
        DeprecatedProduct deprecatedProduct = new DeprecatedProduct("Ball");

        Mockito.when(deprecatedProductRepository.findById(1))
                .thenReturn(deprecatedProduct);
    }

    @Test
    public void whenValidId_thenProductShouldBeFound() {
        DeprecatedProduct found = deprecatedProductService.getProductById(1L);

        Assertions.assertEquals("Ball", found.getName());
    }

}
