package com.example.backend.data.response;

import jakarta.persistence.Column;

public record SeatResponse(
        int seatId,
        int movieHallId,
        String row,
        String type,
        int number,
        float price,
        boolean isOccupied
) {


}
