package gr.aueb.invoicesmanagement.controller;

import gr.aueb.invoicesmanagement.InvoicesManagementApplication;
import gr.aueb.invoicesmanagement.model.DeprecatedProduct;
import gr.aueb.invoicesmanagement.service.DeprecatedProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(value = ProductApiController.class)
@SpringBootTest(
        classes = InvoicesManagementApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class DeprecatedDeprecatedProductApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DeprecatedProductService service;

    @WithMockUser(value = "spring")
    @Test
    public void givenId_whenGetProduct_thenReturnProduct() throws Exception {

        DeprecatedProduct deprecatedProduct = new DeprecatedProduct("Ball");

        given(service.getProductById(1L)).willReturn(deprecatedProduct);

        mvc.perform(get("/api/deprecated/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(deprecatedProduct.getName())));
    }

}
