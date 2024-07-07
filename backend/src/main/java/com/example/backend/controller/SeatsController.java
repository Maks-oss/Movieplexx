package com.example.backend.controller;

import com.example.backend.service.SeatsSqlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("seats")
public class SeatsController {
    private final SeatsSqlService seatsSqlService;

    public SeatsController(SeatsSqlService seatsSqlService) {
        this.seatsSqlService = seatsSqlService;
    }

    @GetMapping("/hall/{hallId}/screening/{screeningId}")
    public ResponseEntity<?> getSeatsForMovieHallAndScreeningSql(@PathVariable int hallId, @PathVariable int screeningId) {
        return ResponseEntity.ok(seatsSqlService.findSeatsForScreeningAndHall(hallId, screeningId));
    }
    /*@PostMapping
    public ResponseEntity<?> pickSeats(@RequestBody List<Integer> seatIds, UriComponentsBuilder uriComponentsBuilder) {
        seatsLogger.log(Level.INFO, "processing payment...");
        seatsLogger.log(Level.INFO, "processing payment...");

        return ResponseEntity.ok().build();
    }*/


}
