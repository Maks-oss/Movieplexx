package com.example.backend.implementation.seats.sql;

import com.example.backend.data.sql.Seat;
import com.example.backend.implementation.seats.SeatResponse;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatsSqlService {
    private final SeatsRepository seatsRepository;

    public SeatsSqlService(SeatsRepository seatsRepository) {
        this.seatsRepository = seatsRepository;
    }

    public List<SeatResponse> findSeatsForScreeningAndHall(int hallId, int screeningId) {
        var seats = seatsRepository.findSeatsByMovieHallId(hallId, Sort.by(Sort.Direction.ASC, "number"));
        List<Seat> seatsByTickets = seatsRepository.findSeatsByScreening(screeningId, hallId);
        return seats.stream()
                .map(seat -> new SeatResponse(
                                seat.getSeatId(),
                                seat.getMovieHall().getId(),
                                seat.getRow(),
                                seat.getType(),
                                seat.getNumber(),
                                seat.getPrice(),
                                seatsByTickets.contains(seat)
                        )
                )
                .collect(Collectors.toList());
    }
}
