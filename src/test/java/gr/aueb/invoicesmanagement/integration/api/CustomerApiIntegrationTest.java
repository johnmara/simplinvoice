package gr.aueb.invoicesmanagement.integration.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.aueb.invoicesmanagement.InvoicesManagementApplication;
import gr.aueb.invoicesmanagement.dao.PrivilegeRepository;
import gr.aueb.invoicesmanagement.dao.RoleRepository;
import gr.aueb.invoicesmanagement.dao.UserRepository;
import gr.aueb.invoicesmanagement.model.Customer;
import gr.aueb.invoicesmanagement.model.Privilege;
import gr.aueb.invoicesmanagement.model.Role;
import gr.aueb.invoicesmanagement.model.User;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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
class CustomerApiIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PrivilegeRepository privilegeRepository;

	static final String USER_EMAIL = "john@john.com";
	static final String USER_PASSWORD = "Asd123!.";

	@BeforeEach
	public void setup() {
		Privilege privilege = privilegeRepository.findByName("AUTHORITY");
		if (privilege == null) {
			privilege = privilegeRepository.saveAndFlush(new Privilege("AUTHORITY"));
		}

		Role role = roleRepository.findByName("ROLE_USER");
		if (role == null) {
			role = new Role("ROLE_USER");
			role.setPrivileges(Collections.singletonList(privilege));
			role = roleRepository.saveAndFlush(role);
		}

		User user = userRepository.findByEmail(USER_EMAIL);
		if (user == null) {
			user = new User();
			user.setFullName("John Mara");
			user.setEmail(USER_EMAIL);
			user.setPassword(new BCryptPasswordEncoder().encode(USER_PASSWORD));
			user.setEnabled(true);
			user.setRoles(Collections.singletonList(role));
			userRepository.saveAndFlush(user);
		}
	}

	@Test
	public void givenId_whenGetCustomer_thenReturnCustomer() throws Exception {
		Customer customer = new Customer("John", "Mara");
		ObjectMapper objectMapper = new ObjectMapper();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBasicAuth(USER_EMAIL, USER_PASSWORD);

		MockHttpServletResponse response = mvc.perform(post("/api/customer/add")
					.contentType(MediaType.APPLICATION_JSON)
					.headers(httpHeaders)
					.content(objectMapper.writeValueAsString(customer)))
				.andExpect(status().isOk()).andReturn().getResponse();

		assertNotNull(response.getContentAsString());
		String generatedId = response.getContentAsString();

		mvc.perform(get("/api/customer/" + generatedId).headers(httpHeaders))
				.andExpect(status().isOk())
				.andExpect(jsonPath("firstName", is(customer.getFirstName())))
				.andExpect(jsonPath("lastName", is(customer.getLastName())));
	}

}
