package com.example.backend.data.nosql;

import com.example.backend.data.sql.Seat;
import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document("MovieHall")
public class MovieHallDocument {
    @Id
    private Integer id;

    private Integer number;

    private String type;

    @DocumentReference
    private CinemaDocument cinema;

    private List<Seat> seatList = new ArrayList<>();

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

    public CinemaDocument getCinema() {
        return cinema;
    }

    public void setCinema(CinemaDocument cinema) {
        this.cinema = cinema;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof MovieHallDocument movieHall)) return false;
        return Objects.equals(getId(), movieHall.getId()) && Objects.equals(getNumber(), movieHall.getNumber()) && Objects.equals(getType(), movieHall.getType()) && Objects.equals(getCinema(), movieHall.getCinema());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber(), getType(), getCinema());
    }
}
