package com.project.StoreManagement.service;

import com.project.StoreManagement.entity.Customer;
import com.project.StoreManagement.exception.CustomerNotFoundException;
import com.project.StoreManagement.repository.CustomerRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // ✅ Update an existing customer
    public Customer updateCustomer(Long customerId, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + customerId + "not found"));

        // Update fields only if they are provided (non-null)
        if (updatedCustomer.getFName() != null)  existingCustomer.setFName(updatedCustomer.getFName());

        if (updatedCustomer.getLName() != null) existingCustomer.setLName(updatedCustomer.getLName());

        if (updatedCustomer.getPhone() != null) existingCustomer.setPhone(updatedCustomer.getPhone());

        if (updatedCustomer.getEmail() != null) existingCustomer.setEmail(updatedCustomer.getEmail());

        // Save updated customer
        return customerRepository.save(existingCustomer);
    }

    // Fetch all customers with overdue Udhaar
    @GetMapping("/defaulters")
    public List<Customer> getDefaulters() {
        return customerRepository.findByOverdueCountGreaterThan(0);
    }
}
