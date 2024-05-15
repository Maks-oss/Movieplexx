package com.example.backend.data;

import jakarta.persistence.*;

@Entity
@Table(name = "employeerole")
public class Employeerole {
    @EmbeddedId
    private EmployeeroleId id;

    @MapsId("employeeid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employeeid", nullable = false)
    private Employee employee;

    @MapsId("roleid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roleid", nullable = false)
    private Role role;

    public EmployeeroleId getId() {
        return id;
    }

    public void setId(EmployeeroleId id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
