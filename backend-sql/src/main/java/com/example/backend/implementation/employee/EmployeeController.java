package com.example.backend.implementation.employee;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public ResponseEntity<?> getEmployees() {
        var employees = employeeRepository.findAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable int id) {
        var employee = employeeRepository.findById(id);
        if (employee.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                new EmployeeResponse(employee.get().getId(),employee.get().getFirstname(),
                                    employee.get().getLastname(),employee.get().getEmail(),employee.get().getRoles(),employee.get().getManagedMovies())
        );
    }
}