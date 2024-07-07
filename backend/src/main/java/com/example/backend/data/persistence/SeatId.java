package com.example.backend.data.persistence;

import java.io.Serializable;
import java.util.Objects;


public class SeatId implements Serializable {
    private int seatId;

    private MovieHall movieHall;

    public SeatId() {
    }

    public SeatId(int seatId, MovieHall movieHall) {
        this.seatId = seatId;
        this.movieHall = movieHall;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public MovieHall getHall() {
        return movieHall;
    }

    public void setHall(MovieHall hallId) {
        this.movieHall = hallId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof SeatId seatId1)) return false;
        return getSeatId() == seatId1.getSeatId() && Objects.equals(movieHall, seatId1.movieHall);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSeatId(), movieHall);
    }
}
