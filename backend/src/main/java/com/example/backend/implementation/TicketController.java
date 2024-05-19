package com.example.backend.implementation;

import com.example.backend.data.Ticket;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ticket")
public class TicketController {
    @PostMapping
    public ResponseEntity<?> generateTicket(@RequestBody Ticket ticket){
        return ResponseEntity.ok().build();
    }

    // in case we have customer tickets page
    @GetMapping
    public ResponseEntity<?> getCustomerTickets() {
        return ResponseEntity.ok().build();
    }
}
