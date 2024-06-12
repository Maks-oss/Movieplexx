package com.example.backend.implementation.employee;

import com.example.backend.data.sql.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
