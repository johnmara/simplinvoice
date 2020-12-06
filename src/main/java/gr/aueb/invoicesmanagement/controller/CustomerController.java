package gr.aueb.invoicesmanagement.controller;

import gr.aueb.invoicesmanagement.model.Customer;
import gr.aueb.invoicesmanagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping(value = "/add")
    public String addCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
        return "OK";
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
