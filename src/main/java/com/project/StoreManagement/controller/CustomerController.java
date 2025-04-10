package com.project.StoreManagement.controller;

import com.project.StoreManagement.entity.Customer;
import com.project.StoreManagement.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/allCustomers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/addCustomer")
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    @PutMapping("/update")
    public Customer updateCustomer(@RequestBody Customer updatedCustomer) {
        return customerService.updateCustomer(updatedCustomer.getCustomerId(), updatedCustomer);
    }

    // API to fetch all defaulters (customers with overdue Udhaar)
    @GetMapping("/defaulters")
    public List<Customer> getDefaulters() {
        return customerService.getDefaulters();
    }
}
