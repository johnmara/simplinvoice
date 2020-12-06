package gr.aueb.invoicesmanagement.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.aueb.invoicesmanagement.InvoicesManagementApplication;
import gr.aueb.invoicesmanagement.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
		classes = InvoicesManagementApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(
		locations = "classpath:application-integrationtest.properties")
class CustomerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void givenId_whenGetCustomer_thenReturnCustomer() throws Exception {

		Customer customer = new Customer("John", "Mara");
		ObjectMapper objectMapper = new ObjectMapper();

		mvc.perform(post("/customer/add").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(customer)))
				.andExpect(status().isOk());

		mvc.perform(get("/customer/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("firstName", is(customer.getFirstName())))
				.andExpect(jsonPath("lastName", is(customer.getLastName())));
	}

}
