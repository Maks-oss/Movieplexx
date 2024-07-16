package com.example.backend.service;

import com.example.backend.data.persistence.Customer;
import com.example.backend.repository.CustomerRepository;
import com.example.backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AuthService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthService(EmployeeRepository employeeRepository, CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var employee = employeeRepository.findEmployeeByEmail(username);
        if (employee != null) {
            return new User(employee.getEmail(), passwordEncoder.encode(employee.getPassword()), Collections.emptyList());
        }
        var customer = customerRepository.findCustomerByEmail(username).orElseThrow(() -> new NoSuchElementException("Customer or employee is not found"));
        return new User(customer.getEmail(), passwordEncoder.encode(customer.getPassword()), Collections.emptyList());
    }
}
