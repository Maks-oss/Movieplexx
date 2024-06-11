package com.example.backend.implementation.tickets;

import com.example.backend.implementation.payment.PaymentService;
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
    public ResponseEntity<?> createTicket(
            @RequestParam String paymentMethod,
            @RequestBody CreateTicketRequestBody createTicketRequestBody
    ) {
        paymentService.processPayment(paymentMethod);
        return ResponseEntity.ok(ticketService.createTicketResponse(createTicketRequestBody));
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
