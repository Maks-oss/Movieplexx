package com.example.backend.data;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @Column(name = "ticketid", nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "screeningid")
    private MovieScreening screening;

    @ManyToOne
    @JoinColumn(name = "customerid")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employeeid")
    private Employee employee;

    @Column(name = "price")
    private Float price;

    @Column(name = "dateofissue")
    private LocalDate dateOfIssue;

    public Ticket() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MovieScreening getScreening() {
        return screening;
    }

    public void setScreening(MovieScreening screening) {
        this.screening = screening;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) &&
               Objects.equals(screening, ticket.screening) &&
               Objects.equals(customer, ticket.customer) &&
               Objects.equals(employee, ticket.employee) &&
               Objects.equals(price, ticket.price) &&
               Objects.equals(dateOfIssue, ticket.dateOfIssue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, screening, customer, employee, price, dateOfIssue);
    }
}
