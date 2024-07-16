package com.example.backend.repository;

import com.example.backend.data.persistence.Customer;
import com.example.backend.data.persistence.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
   Employee findEmployeeByEmail(String email);

}
