package com.example.backend.data.nosql;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Set;

@Document("Role")
public class RoleDocument {
    @Id
    private Integer id;

    private String name;

    private String description;

    private Set<EmployeeDocument> employees;

    public RoleDocument(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public RoleDocument() {
    }

    public Set<EmployeeDocument> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeDocument> employees) {
        this.employees = employees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
