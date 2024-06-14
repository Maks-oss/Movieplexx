package com.example.backend.migration.transform.reader;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component("ticket_reader")
public class TicketReader extends SqlItemReader<Map> {
    @Override
    @Transactional
    public List<Map> readItems() {
       return entityManager.createQuery("""
                SELECT
                                 t.price AS price,
                                 t.dateOfIssue AS date, 
                                 t.screening.id AS screeningId, 
                                 t.seat.id AS seatId, 
                                 t.customer.id AS customerId,
                                 t.seat.movieHall.id AS moviehallId
                             FROM 
                                 Ticket t
                """, Map.class).getResultList();
    }
}
