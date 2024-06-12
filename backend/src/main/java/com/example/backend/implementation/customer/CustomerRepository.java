package com.example.backend.implementation.customer;

import com.example.backend.data.sql.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
