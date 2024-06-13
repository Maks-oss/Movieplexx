package com.example.backend.data.sql;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Objects;

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

    @ManyToOne
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof MovieHall movieHall)) return false;
        return Objects.equals(getId(), movieHall.getId()) && Objects.equals(getNumber(), movieHall.getNumber()) && Objects.equals(getType(), movieHall.getType()) && Objects.equals(getCinema(), movieHall.getCinema());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber(), getType(), getCinema());
    }
}
