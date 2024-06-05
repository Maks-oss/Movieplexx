package com.example.backend.implementation.seats;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("seats")
public class SeatsController {
    private final SeatsRepository seatsRepository;

    public SeatsController(SeatsRepository seatsRepository) {
        this.seatsRepository = seatsRepository;
    }

    @GetMapping("/{movieHallId}")
    public ResponseEntity<?> getSeatsForMovieHall(@PathVariable int movieHallId) {
        return ResponseEntity.ok(seatsRepository.findSeatsByMovieHallId(movieHallId, Sort.by(Sort.Direction.ASC, "number")));
    }
    /*@PostMapping
    public ResponseEntity<?> pickSeats(@RequestBody List<Integer> seatIds, UriComponentsBuilder uriComponentsBuilder) {
        seatsLogger.log(Level.INFO, "processing payment...");
        seatsLogger.log(Level.INFO, "processing payment...");

        return ResponseEntity.ok().build();
    }*/


}
