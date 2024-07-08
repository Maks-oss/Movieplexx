package com.example.backend.controller;

import com.example.backend.service.PaymentService;
import com.example.backend.data.request.TicketRequestBody;
import com.example.backend.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tickets")
public class TicketController {
    private final TicketService ticketService;
    private final PaymentService paymentService;

    public TicketController(TicketService ticketService, PaymentService paymentService) {
        this.ticketService = ticketService;
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<?> createTicketSql(
            @RequestParam String paymentMethod,
            @RequestBody TicketRequestBody ticketRequestBody
    ) {
        paymentService.processPayment(paymentMethod);
        return ResponseEntity.ok(ticketService.createTicketResponse(ticketRequestBody));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerTickets(
            @PathVariable int id
    ) {
        return ResponseEntity.ok(ticketService.getCustomerTickets(id));
    }

    @GetMapping("/clear")
    public ResponseEntity<?> clearTickets() {
        ticketService.clearAll();
        return ResponseEntity.noContent().build();
    }
}
