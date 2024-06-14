package com.example.backend.implementation.employee;

import com.example.backend.data.nosql.EmployeeDocument;
import com.example.backend.data.sql.Employee;
import com.example.backend.data.sql.Role;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final MongoTemplate mongoTemplate;

    public EmployeeController(EmployeeRepository employeeRepository, MongoTemplate mongoTemplate) {
        this.employeeRepository = employeeRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/sql")
    public ResponseEntity<?> getEmployeesSql() {
        var employees = employeeRepository.findAll();
        return ResponseEntity.ok(employees);
    }
    @GetMapping("/nosql")
    public ResponseEntity<?> getEmployeesNoSql() {
        return ResponseEntity.ok(mongoTemplate.findAll(EmployeeDocument.class));
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

    @GetMapping("/nosql/{id}")
    public ResponseEntity<?> getEmployeeByIdNoSql(@PathVariable int id) {
        var employee = mongoTemplate.findById(id, EmployeeDocument.class);
        if (employee == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                new EmployeeResponse(employee.getId(),employee.getFirstname(),
                        employee.getLastname(),employee.getEmail(),
                        employee.getRoles().stream().map(r -> new Role(r.getName(), r.getDescription())).collect(Collectors.toSet())
                )
        );
    }
}
