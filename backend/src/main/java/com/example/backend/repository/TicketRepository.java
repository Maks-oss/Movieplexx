package com.example.backend.repository;

import com.example.backend.data.persistence.MovieScreening;
import com.example.backend.data.persistence.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, MovieScreening> {

    @Query("""
            SELECT t FROM Ticket t 
            INNER JOIN Customer c on c.id = t.customer.id
            WHERE c.id = :customerId
            """)
    List<Ticket> findTicketsByCustomerId(int customerId);
}
