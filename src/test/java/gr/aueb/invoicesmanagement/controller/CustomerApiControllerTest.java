package gr.aueb.invoicesmanagement.controller;

import gr.aueb.invoicesmanagement.InvoicesManagementApplication;
import gr.aueb.invoicesmanagement.model.Customer;
import gr.aueb.invoicesmanagement.service.CustomerService;
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

//@WebMvcTest(value = CustomerApiController.class)
@SpringBootTest(
        classes = InvoicesManagementApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class CustomerApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService service;

    @WithMockUser(value = "spring")
    @Test
    public void givenId_whenGetCustomer_thenReturnCustomer() throws Exception {

        Customer customer = new Customer("John", "Mara");

        given(service.getCustomerById(1L)).willReturn(customer);

        mvc.perform(get("/api/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", is(customer.getFirstName())))
                .andExpect(jsonPath("lastName", is(customer.getLastName())));
    }

}
