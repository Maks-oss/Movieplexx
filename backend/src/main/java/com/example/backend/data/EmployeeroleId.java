package com.example.backend.data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmployeeroleId implements Serializable {
    private static final long serialVersionUID = -3242598976321408556L;
    @Column(name = "employeeid", nullable = false)
    private Integer employeeId;

    @Column(name = "roleid", nullable = false)
    private Integer roleId;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EmployeeroleId entity = (EmployeeroleId) o;
        return Objects.equals(this.roleId, entity.roleId) &&
                Objects.equals(this.employeeId, entity.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, employeeId);
    }

}
