package com.example.backend.implementation;

import com.example.backend.data.Seat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("seats")
public class SeatsController {

    @GetMapping
    public ResponseEntity<?> getAvailableSeats() {
        return ResponseEntity.ok().build();
    }
    @PostMapping
    public ResponseEntity<?> pickSeats(@RequestBody Seat seat, UriComponentsBuilder uriComponentsBuilder) {
        return ResponseEntity.created(uriComponentsBuilder.build().toUri()).build();
    }


}
