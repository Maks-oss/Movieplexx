package com.example.backend.data.sql;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "ticket")
@IdClass(TicketId.class)
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

    @Id @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "screeningid")
    private MovieScreening screening;

    @Id @ManyToOne
    @JoinColumn(name = "seatid")
//    @JoinColumns({
//            @JoinColumn(name = "seatid", referencedColumnName = "seatid"),
//            @JoinColumn(name = "hallid", referencedColumnName = "hallid")
//    })
    private Seat seat;



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


    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
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
        return
               Objects.equals(screening, ticket.screening) &&
               Objects.equals(customer, ticket.customer) &&
               Objects.equals(employee, ticket.employee) &&
               Objects.equals(price, ticket.price) &&
               Objects.equals(dateOfIssue, ticket.dateOfIssue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(screening, customer, employee, price, dateOfIssue);
    }
}
