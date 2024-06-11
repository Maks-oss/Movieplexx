package com.example.backend.data.sql;

import java.io.Serializable;
import java.util.Objects;
public class TicketId implements Serializable {
    private Seat seat;

    private MovieScreening screening;

    public Seat getSeatId() {
        return seat;
    }

    public void setSeatId(Seat seatId) {
        this.seat = seatId;
    }

    public MovieScreening getScreening() {
        return screening;
    }

    public void setScreening(MovieScreening screening) {
        this.screening = screening;
    }

    public TicketId(Seat seatId, MovieScreening screening) {
        this.seat = seatId;
        this.screening = screening;
    }

    public TicketId() {
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof TicketId ticketId)) return false;
        return Objects.equals(seat, ticketId.seat) && Objects.equals(getScreening(), ticketId.getScreening());
    }

    @Override
    public int hashCode() {
        return Objects.hash(seat, getScreening());
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
