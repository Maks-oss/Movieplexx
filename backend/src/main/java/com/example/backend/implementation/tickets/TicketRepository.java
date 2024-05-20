package com.example.backend.implementation.tickets;

import com.example.backend.data.Seat;
import com.example.backend.data.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query("""
            SELECT t FROM Ticket t 
            INNER JOIN Customer c on c.id = t.customer.id
            WHERE c.id = :customerId
            """)
    List<Ticket> findTicketsByCustomerId(int customerId);
}
