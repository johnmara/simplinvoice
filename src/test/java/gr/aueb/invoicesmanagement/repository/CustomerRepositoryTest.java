package gr.aueb.invoicesmanagement.repository;

import gr.aueb.invoicesmanagement.dao.CustomerRepository;
import gr.aueb.invoicesmanagement.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    CustomerRepository repository;

    @Test
    public void returnAllCustomers() {
        // given
        Customer customer1 = new Customer("John", "Mara");
        Customer customer2 = new Customer("Zoi", "Frt");
        entityManager.persist(customer1);
        entityManager.persist(customer2);
        entityManager.flush();

        // when
        List<Customer> found = (List<Customer>) repository.findAll();

        // then
        Assertions.assertEquals(2, found.size());
    }

    @Test
    public void addCustomer() {
        // given
        Customer customer1 = new Customer("John", "Mara");
        Long id = repository.save(customer1).getId();

        // when
        Customer found = repository.findById(id).orElse(null);

        // then
        Assertions.assertEquals("John", found.getFirstName());
        Assertions.assertEquals("Mara", found.getLastName());
    }

}
