//package gr.aueb.invoicesmanagement.controller;
//
//import gr.aueb.invoicesmanagement.controller.api.CustomerApiController;
//import gr.aueb.invoicesmanagement.dao.UserRepository;
//import gr.aueb.invoicesmanagement.model.Customer;
//import gr.aueb.invoicesmanagement.model.User;
//import gr.aueb.invoicesmanagement.service.CustomerService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.hamcrest.Matchers.is;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(CustomerApiController.class)
//@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
//public class CustomerApiControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private CustomerService service;
//
////    @MockBean
////    MyUserDetailsService userDetailsService;
//
//    @MockBean
//    UserRepository userRepository;
//
//    static final String USER_EMAIL = "john@john.com";
//    static final String USER_PASSWORD = "Asd123!.";
//
//    @BeforeEach
//    public void setup() {
////        Mockito.when(userDetailsService.loadUserByUsername(USER_EMAIL)).thenReturn(
////                new org.springframework.security.core.userdetails.User(USER_EMAIL, USER_PASSWORD, true,
////                        true, true, true, Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER")))
////        );
//
//        Mockito.when(userRepository.findByEmail(USER_EMAIL)).thenReturn(new User());
//
//    }
//
//    @WithUserDetails(value = "user")
//    @Test
//    public void givenId_whenGetCustomer_thenReturnCustomer() throws Exception {
//
//        Customer customer = new Customer("John", "Mara");
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setBasicAuth(USER_EMAIL, USER_PASSWORD);
//
//        given(service.getCustomerById(1L)).willReturn(customer);
//
//        mvc.perform(get("/customer/1").headers(httpHeaders))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("firstName", is(customer.getFirstName())))
//                .andExpect(jsonPath("lastName", is(customer.getLastName())));
//    }
//
//}
