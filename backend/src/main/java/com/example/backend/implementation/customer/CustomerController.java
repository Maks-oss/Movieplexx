package com.example.backend.implementation.customer;

import com.example.backend.data.sql.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public ResponseEntity<?> getCustomers() {
        var customers = customerRepository.findAll();
        List<CustomerResponse> response = new ArrayList<>();
        for (Customer el :
                customers) {
            response.add(new CustomerResponse(el.getId(),el.getFirstname(),el.getLastname(),el.getEmail()));
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable int id) {
        var customer = customerRepository.findById(id);
        if (customer.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                new CustomerResponse(customer.get().getId(),customer.get().getFirstname(),
                                    customer.get().getLastname(),customer.get().getEmail())
        );
    }
}
