package com.example.backend.implementation.tickets;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ticket")
public class TicketController {
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<?> generateTicket(@RequestBody TicketGenerationData ticketGenerationData) {
//        var ticket = ticketService.createTicket(ticketGenerationData);
//        var reservedSeats = seatsService.reserveSeats(ticketGenerationData.seatsIds(), ticket);
//        return ResponseEntity.ok(
////                new TicketResponse(ticket.getId(), ticket.getPrice(), ticket.getDateOfIssue(),reservedSeats.get(0).getMovieHall().)
////                Map.of(
////                        "ticket", ticket,
////                        "seats", reservedSeats
////                )
//        );
        return ResponseEntity.ok(ticketService.createTicketResponse(ticketGenerationData));

    }
    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomerTickets(@PathVariable int customerId) {
        return ResponseEntity.ok(ticketService.getCustomerTickets(customerId));
    }

    @GetMapping("/clear")
    public ResponseEntity<?> clearTickets() {
        ticketService.clearAll();
        return ResponseEntity.noContent().build();
    }
}
