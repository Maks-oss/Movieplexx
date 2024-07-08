package com.example.backend.controller;

import com.example.backend.service.SeatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("moviehall")
public class MovieHallController {
    private final SeatsService seatsService;

    public MovieHallController(SeatsService seatsService) {
        this.seatsService = seatsService;
    }

    @GetMapping("/{hallId}/screening/{screeningId}/seats")
    public ResponseEntity<?> getSeatsForMovieHallAndScreeningSql(@PathVariable int hallId, @PathVariable int screeningId) {
        return ResponseEntity.ok(seatsService.findSeatsForScreeningAndHall(hallId, screeningId));
    }
    /*@PostMapping
    public ResponseEntity<?> pickSeats(@RequestBody List<Integer> seatIds, UriComponentsBuilder uriComponentsBuilder) {
        seatsLogger.log(Level.INFO, "processing payment...");
        seatsLogger.log(Level.INFO, "processing payment...");

        return ResponseEntity.ok().build();
    }*/


}
