package gr.aueb.invoicesmanagement.controller;

import gr.aueb.invoicesmanagement.model.Customer;
import gr.aueb.invoicesmanagement.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService service;

    @Test
    public void givenId_whenGetCustomer_thenReturnCustomer() throws Exception {

        Customer customer = new Customer("John", "Mara");

        given(service.getCustomerById(1L)).willReturn(customer);

        mvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", is(customer.getFirstName())))
                .andExpect(jsonPath("lastName", is(customer.getLastName())));
    }

}
