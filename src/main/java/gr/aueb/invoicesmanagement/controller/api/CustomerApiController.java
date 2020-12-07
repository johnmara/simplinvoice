package gr.aueb.invoicesmanagement.controller.api;

import gr.aueb.invoicesmanagement.model.Customer;
import gr.aueb.invoicesmanagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/customer")
public class CustomerApiController {

    @Autowired
    CustomerService customerService;

    @PostMapping(value = "/add")
    public String addCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer).getId().toString();
    }

    @GetMapping(value = "/list")
    public Object getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping(value = "/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

}
