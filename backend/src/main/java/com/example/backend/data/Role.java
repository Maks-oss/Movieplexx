package com.example.backend.data;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @Column(name = "roleid", nullable = false)
    private Integer id;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<Employee> employees;

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
