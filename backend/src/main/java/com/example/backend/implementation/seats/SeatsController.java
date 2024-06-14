package com.example.backend.implementation.seats;

import com.example.backend.implementation.seats.nosql.SeatsNoSqlService;
import com.example.backend.implementation.seats.sql.SeatsSqlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("seats")
public class SeatsController {
    private final SeatsSqlService seatsSqlService;
    private final SeatsNoSqlService seatsNoSqlService;

    public SeatsController(SeatsSqlService seatsSqlService, SeatsNoSqlService seatsNoSqlService) {
        this.seatsSqlService = seatsSqlService;
        this.seatsNoSqlService = seatsNoSqlService;
    }

    @GetMapping("/sql/hall/{hallId}/screening/{screeningId}")
    public ResponseEntity<?> getSeatsForMovieHallAndScreeningSql(@PathVariable int hallId, @PathVariable int screeningId) {
        return ResponseEntity.ok(seatsSqlService.findSeatsForScreeningAndHall(hallId, screeningId));
    }
    @GetMapping("/nosql/hall/{hallId}/screening/{screeningId}")
    public ResponseEntity<?> getSeatsForMovieHallAndScreeningNoSql(@PathVariable int hallId, @PathVariable int screeningId) {
        return ResponseEntity.ok(seatsNoSqlService.findSeatsForScreeningAndHall(hallId, screeningId));
    }
    /*@PostMapping
    public ResponseEntity<?> pickSeats(@RequestBody List<Integer> seatIds, UriComponentsBuilder uriComponentsBuilder) {
        seatsLogger.log(Level.INFO, "processing payment...");
        seatsLogger.log(Level.INFO, "processing payment...");

        return ResponseEntity.ok().build();
    }*/


}
