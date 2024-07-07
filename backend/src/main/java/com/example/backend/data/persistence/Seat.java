package com.example.backend.data.persistence;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "seat")
//@IdClass(SeatId.class)
public class Seat {
    @Id @GeneratedValue
    @Column(name = "seatid", nullable = false)
    private Integer seatId;

    @ManyToOne
    @JoinColumn(name = "hallid")
    private MovieHall movieHall;


    @Column(name = "\"row\"", length = Integer.MAX_VALUE)
    private String row;

    @Column(name = "type", length = Integer.MAX_VALUE)
    private String type;

    @Column(name = "number")
    private Integer number;

    @Column(name = "price")
    private Float price;

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public void setMovieHall(MovieHall movieHall) {
        this.movieHall = movieHall;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public MovieHall getMovieHall() {
        return movieHall;
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


    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Seat{" +
               ", row='" + row + '\'' +
               ", number=" + number +
               '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Seat seat)) return false;
        return Objects.equals(getSeatId(), seat.getSeatId()) && Objects.equals(getMovieHall(), seat.getMovieHall()) && Objects.equals(getRow(), seat.getRow()) && Objects.equals(getType(), seat.getType()) && Objects.equals(getNumber(), seat.getNumber()) && Objects.equals(getPrice(), seat.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSeatId(), getMovieHall(), getRow(), getType(), getNumber(), getPrice());
    }
}
