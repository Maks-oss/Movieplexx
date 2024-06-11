package com.example.backend.implementation.seats;

import com.example.backend.data.sql.Seat;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("seats")
public class SeatsController {
    private final SeatsRepository seatsRepository;

    public SeatsController(SeatsRepository seatsRepository) {
        this.seatsRepository = seatsRepository;
    }

    @GetMapping("/hall/{hallId}/screening/{screeningId}")
    public ResponseEntity<?> getSeatsForMovieHallAndScreening(@PathVariable int hallId, @PathVariable int screeningId) {
        var seats = seatsRepository.findSeatsByMovieHallId(hallId, Sort.by(Sort.Direction.ASC, "number"));
        List<Seat> seatsByTickets = seatsRepository.findSeatsByScreening(screeningId, hallId);
        return ResponseEntity.ok(seats.stream()
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
                .collect(Collectors.toList()));
    }
    /*@PostMapping
    public ResponseEntity<?> pickSeats(@RequestBody List<Integer> seatIds, UriComponentsBuilder uriComponentsBuilder) {
        seatsLogger.log(Level.INFO, "processing payment...");
        seatsLogger.log(Level.INFO, "processing payment...");

        return ResponseEntity.ok().build();
    }*/


}
