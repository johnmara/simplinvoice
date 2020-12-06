package gr.aueb.invoicesmanagement.service;

import gr.aueb.invoicesmanagement.dao.CustomerRepository;
import gr.aueb.invoicesmanagement.model.Customer;
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
public class CustomerServiceTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public CustomerService customerService() {
            return new CustomerService();
        }

    }

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        Customer customer = new Customer("John", "Mara");

        Mockito.when(customerRepository.findById(1))
                .thenReturn(customer);
    }

    @Test
    public void whenValidId_thenCustomerShouldBeFound() {
        Customer found = customerService.getCustomerById(1L);

        Assertions.assertEquals("John", found.getFirstName());
        Assertions.assertEquals("Mara", found.getLastName());
    }

}
