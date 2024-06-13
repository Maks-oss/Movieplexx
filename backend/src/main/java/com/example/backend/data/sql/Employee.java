package com.example.backend.data.sql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee {
    @Id @GeneratedValue
    @Column(name = "employeeid", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "managerid")
    private Employee manager;

    @Column(name = "firstname", length = Integer.MAX_VALUE)
    private String firstname;

    @Column(name = "lastname", length = Integer.MAX_VALUE)
    private String lastname;

    @Column(name = "email", length = Integer.MAX_VALUE)
    private String email;

    @Column(name = "password", length = Integer.MAX_VALUE)
    private String password;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "employeerole",
            joinColumns = @JoinColumn(name = "employeeid"),
            inverseJoinColumns = @JoinColumn(name = "roleid"))
    private Set<Role> roles;
    @OneToMany(mappedBy = "manager")
    @JsonIgnore
    private Set<Movie> managedMovies;
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Set<Movie> getManagedMovies() {
        return managedMovies;
    }

    public void setManagedMovies(Set<Movie> managedMovies) {
        this.managedMovies = managedMovies;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", manager=" + manager +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", managedMovies=" + managedMovies +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Employee employee)) return false;
        return Objects.equals(getId(), employee.getId()) && Objects.equals(getManager(), employee.getManager()) && Objects.equals(getFirstname(), employee.getFirstname()) && Objects.equals(getLastname(), employee.getLastname()) && Objects.equals(getEmail(), employee.getEmail()) && Objects.equals(getPassword(), employee.getPassword()) && Objects.equals(getRoles(), employee.getRoles()) && Objects.equals(getManagedMovies(), employee.getManagedMovies());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getManager(), getFirstname(), getLastname(), getEmail(), getPassword(), getRoles(), getManagedMovies());
    }
}
