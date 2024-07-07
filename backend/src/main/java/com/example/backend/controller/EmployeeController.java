package com.example.backend.controller;

import com.example.backend.repository.EmployeeRepository;
import com.example.backend.data.response.EmployeeResponse;
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
    public ResponseEntity<?> getEmployeesSql() {
        var employees = employeeRepository.findAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/sql/{id}")
    public ResponseEntity<?> getEmployeeByIdSql(@PathVariable int id) {
        var employee = employeeRepository.findById(id);
        if (employee.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                new EmployeeResponse(employee.get().getId(),employee.get().getFirstname(),
                                    employee.get().getLastname(),employee.get().getEmail(),employee.get().getRoles())
        );
    }

}
