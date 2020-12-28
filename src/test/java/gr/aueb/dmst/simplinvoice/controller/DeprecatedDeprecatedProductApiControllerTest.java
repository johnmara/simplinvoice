package gr.aueb.dmst.simplinvoice.controller;

import gr.aueb.dmst.simplinvoice.SimplinvoiceApplication;
import gr.aueb.dmst.simplinvoice.model.DeprecatedProduct;
import gr.aueb.dmst.simplinvoice.service.DeprecatedProductService;
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
        classes = SimplinvoiceApplication.class
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
