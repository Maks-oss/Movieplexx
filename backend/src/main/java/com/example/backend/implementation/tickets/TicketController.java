package com.example.backend.implementation.tickets;

import com.example.backend.implementation.payment.PaymentService;
import com.example.backend.implementation.tickets.nosql.TicketNoSqlService;
import com.example.backend.implementation.tickets.sql.TicketSqlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tickets")
public class TicketController {
    private final TicketSqlService ticketSqlService;
    private final TicketNoSqlService ticketNoSqlService;
    private final PaymentService paymentService;

    public TicketController(TicketSqlService ticketSqlService, TicketNoSqlService ticketNoSqlService, PaymentService paymentService) {
        this.ticketSqlService = ticketSqlService;
        this.ticketNoSqlService = ticketNoSqlService;
        this.paymentService = paymentService;
    }

    @PostMapping("/sql")
    public ResponseEntity<?> createTicketSql(
            @RequestParam String paymentMethod,
            @RequestBody CreateTicketRequestBody createTicketRequestBody
    ) {
        paymentService.processPayment(paymentMethod);
        return ResponseEntity.ok(ticketSqlService.createTicketResponse(createTicketRequestBody));
    }

    @GetMapping("/sql/{id}")
    public ResponseEntity<?> getCustomerTickets(
            @PathVariable int id
    ) {
        return ResponseEntity.ok(ticketSqlService.getCustomerTickets(id));
    }

    @PostMapping("/nosql")
    public ResponseEntity<?> createTicketNoSql(
            @RequestParam String paymentMethod,
            @RequestBody CreateTicketRequestBody createTicketRequestBody
    ) {
        paymentService.processPayment(paymentMethod);
        return ResponseEntity.ok(ticketNoSqlService.createTicketResponse(createTicketRequestBody));
    }

    @GetMapping("/clear")
    public ResponseEntity<?> clearTickets() {
        ticketSqlService.clearAll();
        return ResponseEntity.noContent().build();
    }
}
