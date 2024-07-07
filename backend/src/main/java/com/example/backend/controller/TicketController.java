package com.example.backend.controller;

import com.example.backend.service.PaymentService;
import com.example.backend.data.request.TicketRequestBody;
import com.example.backend.service.TicketSqlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tickets")
public class TicketController {
    private final TicketSqlService ticketSqlService;
    private final PaymentService paymentService;

    public TicketController(TicketSqlService ticketSqlService, PaymentService paymentService) {
        this.ticketSqlService = ticketSqlService;
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<?> createTicketSql(
            @RequestParam String paymentMethod,
            @RequestBody TicketRequestBody ticketRequestBody
    ) {
        paymentService.processPayment(paymentMethod);
        return ResponseEntity.ok(ticketSqlService.createTicketResponse(ticketRequestBody));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerTickets(
            @PathVariable int id
    ) {
        return ResponseEntity.ok(ticketSqlService.getCustomerTickets(id));
    }

    @GetMapping("/clear")
    public ResponseEntity<?> clearTickets() {
        ticketSqlService.clearAll();
        return ResponseEntity.noContent().build();
    }
}
