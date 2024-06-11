package com.example.backend.data.nosql;

import java.io.Serializable;
import java.util.Objects;
public class TicketId implements Serializable {
    private Seat seat;
    private MovieHall movieHall;

    private MovieScreening screening;

    public TicketId() {
    }

    public TicketId(Seat seat, MovieHall movieHall, MovieScreening screening) {
        this.seat = seat;
        this.movieHall = movieHall;
        this.screening = screening;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof TicketId ticketId)) return false;
        return Objects.equals(getSeat(), ticketId.getSeat()) && Objects.equals(getMovieHall(), ticketId.getMovieHall()) && Objects.equals(getScreening(), ticketId.getScreening());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSeat(), getMovieHall(), getScreening());
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public MovieHall getMovieHall() {
        return movieHall;
    }

    public void setMovieHall(MovieHall movieHall) {
        this.movieHall = movieHall;
    }

    public MovieScreening getScreening() {
        return screening;
    }

    public void setScreening(MovieScreening screening) {
        this.screening = screening;
    }

    @Override
    public String toString() {
        return "TicketId{" +
               "seat=" + seat +
               ", movieHall=" + movieHall +
               ", screening=" + screening +
               '}';
    }
}
//@Embeddable
//public class TicketId implements Serializable {
//    private SeatId seatId;
//    private Integer screeningId;
//
//    public SeatId getSeatId() {
//        return seatId;
//    }
//
//    public void setSeatId(SeatId seatId) {
//        this.seatId = seatId;
//    }
//
//    public Integer getScreeningId() {
//        return screeningId;
//    }
//
//    public void setScreeningId(Integer screeningId) {
//        this.screeningId = screeningId;
//    }
//
//    public TicketId() {}
//
//    public TicketId(SeatId seatId, Integer screeningId) {
//        this.seatId = seatId;
//        this.screeningId = screeningId;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        TicketId ticketId = (TicketId) o;
//        return Objects.equals(seatId, ticketId.seatId) &&
//               Objects.equals(screeningId, ticketId.screeningId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(seatId, screeningId);
//    }
//}
