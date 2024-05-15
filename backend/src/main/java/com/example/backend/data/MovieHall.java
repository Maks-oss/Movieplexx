package com.example.backend.data;

import jakarta.persistence.*;

@Entity
@Table(name = "moviehall")
public class MovieHall {
    @Id @GeneratedValue
    @Column(name = "hallid", nullable = false)
    private Integer id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "type", length = Integer.MAX_VALUE)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinemaid")
    private Cinema cinema;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

}
