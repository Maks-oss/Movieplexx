package com.example.backend.data.nosql;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.util.Objects;

@Document
public class Ticket {
//    @Id
//    @Column(name = "ticketid", nullable = false)
//    private String id;
//
//    @ManyToOne
//    @JoinColumn(name = "screeningid")
//    private MovieScreening screening;
//    @Id
//    @ManyToOne
////    @JoinColumns({
////            @JoinColumn(name = "seatid", referencedColumnName = "seatid"),
////            @JoinColumn(name = "hallid", referencedColumnName = "hallid")
////    })
//    private Seat seat;

//    @Id
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "screeningid", nullable = false)
//    private MovieScreening screening;

//    @EmbeddedId
//    private TicketId id;

//    @Id @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "screeningid")
//    private MovieScreening screening;
//
//    @Id @ManyToOne
//    @JoinColumn(name = "seatid")
////    @JoinColumns({
////            @JoinColumn(name = "seatid", referencedColumnName = "seatid"),
////            @JoinColumn(name = "hallid", referencedColumnName = "hallid")
////    })
//    private Seat seat;
    @Id
    private TicketId ticketId;


    @DocumentReference
    private CustomerDocument customer;

    @DocumentReference
    private Employee employee;

    private Float price;

    private LocalDate dateOfIssue;


    public Seat getSeat() {
        return ticketId.getSeat();
    }

    public void setSeat(Seat seat) {
        ticketId.setSeat(seat);
    }

    public MovieScreening getScreening() {
        return ticketId.getScreening();
    }

    public void setScreening(MovieScreening screening) {
        ticketId.setScreening(screening);
    }

    public CustomerDocument getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDocument customerDocument) {
        this.customer = customerDocument;
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
        return
               Objects.equals(ticketId.getScreening(), ticket.ticketId.getScreening()) &&
               Objects.equals(customer, ticket.customer) &&
               Objects.equals(employee, ticket.employee) &&
               Objects.equals(price, ticket.price) &&
               Objects.equals(dateOfIssue, ticket.dateOfIssue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId.getScreening(), customer, employee, price, dateOfIssue);
    }
}
