package gr.aueb.invoicesmanagement.integration.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.aueb.invoicesmanagement.InvoicesManagementApplication;
import gr.aueb.invoicesmanagement.model.DeprecatedProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
class DeprecatedProductApiIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@WithMockUser(value = "spring")
	@Test
	public void givenId_whenGetProduct_thenReturnProduct() throws Exception {
		DeprecatedProduct deprecatedProduct = new DeprecatedProduct("Ball");
		ObjectMapper objectMapper = new ObjectMapper();

		HttpHeaders httpHeaders = new HttpHeaders();

		MockHttpServletResponse response = mvc.perform(post("/api/deprecated/product/add")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(deprecatedProduct)))
				.andExpect(status().isOk()).andReturn().getResponse();

		assertNotNull(response.getContentAsString());
		String generatedId = response.getContentAsString();

		mvc.perform(get("/api/deprecated/product/" + generatedId).headers(httpHeaders))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name", is(deprecatedProduct.getName())));
	}

}
