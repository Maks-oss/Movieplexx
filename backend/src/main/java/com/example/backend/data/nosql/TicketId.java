package com.example.backend.data.nosql;

import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

public class TicketId implements Serializable {

    @Field("seatId")
    private Integer seatId;

    @Field("hallId")
    private Integer hallId;

    @Field("screeningId")
    private Integer screeningId;

    // Getters and Setters
    public Integer getSeatId() {
        return seatId;
    }

    public TicketId(Integer seatId, Integer hallId, Integer screeningId) {
        this.seatId = seatId;
        this.hallId = hallId;
        this.screeningId = screeningId;
    }

    public TicketId() {
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public Integer getHallId() {
        return hallId;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }

    public Integer getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(Integer screeningId) {
        this.screeningId = screeningId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketId ticketId = (TicketId) o;
        return Objects.equals(seatId, ticketId.seatId) &&
               Objects.equals(hallId, ticketId.hallId) &&
               Objects.equals(screeningId, ticketId.screeningId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatId, hallId, screeningId);
    }

    @Override
    public String toString() {
        return "TicketId{" +
               "seatId=" + seatId +
               ", hallId=" + hallId +
               ", screeningId=" + screeningId +
               '}';
    }
}
