package com.example.backend.implementation.seats;

import com.example.backend.data.Seat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("seats")
public class SeatsController {
    private final SeatsRepository seatsRepository;
    private final Logger seatsLogger = Logger.getLogger("seats");

    public SeatsController(SeatsRepository seatsRepository) {
        this.seatsRepository = seatsRepository;
    }

    @GetMapping("/{movieHallId}")
    public ResponseEntity<?> getSeatsForMovieHall(@PathVariable int movieHallId) {
        return ResponseEntity.ok(seatsRepository.findSeatsByMovieHallId(movieHallId));
    }
    /*@PostMapping
    public ResponseEntity<?> pickSeats(@RequestBody List<Integer> seatIds, UriComponentsBuilder uriComponentsBuilder) {
        seatsLogger.log(Level.INFO, "processing payment...");
        seatsLogger.log(Level.INFO, "processing payment...");

        return ResponseEntity.ok().build();
    }*/


}
