package com.example.backend.data.nosql;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document("Customer")
public class CustomerDocument {
    @Id
    private Integer id;

    private String firstname;

    private String lastname;

    private String email;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof com.example.backend.data.sql.Customer customer)) return false;
        return Objects.equals(getId(), customer.getId()) && Objects.equals(getFirstname(), customer.getFirstname()) && Objects.equals(getLastname(), customer.getLastname()) && Objects.equals(getEmail(), customer.getEmail()) && Objects.equals(getPassword(), customer.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstname(), getLastname(), getEmail(), getPassword());
    }
}
