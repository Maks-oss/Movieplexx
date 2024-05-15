package com.example.backend.data;

import jakarta.persistence.*;

@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @Column(name = "seatid", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hallid")
    private MovieHall hallId;

    @Column(name = "\"row\"", length = Integer.MAX_VALUE)
    private String row;

    @Column(name = "type", length = Integer.MAX_VALUE)
    private String type;

    @Column(name = "number")
    private Integer number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticketid")
    private Ticket ticket;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MovieHall getHallId() {
        return hallId;
    }

    public void setHallId(MovieHall hallId) {
        this.hallId = hallId;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

}
