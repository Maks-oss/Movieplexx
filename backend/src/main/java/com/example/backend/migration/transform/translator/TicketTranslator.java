package com.example.backend.migration.transform.translator;

import com.example.backend.data.nosql.TicketDocument;
import com.example.backend.data.nosql.TicketId;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("ticket_translator")
public class TicketTranslator implements ItemTranslator<Map, TicketDocument> {

    @Override
    public List<TicketDocument> transformData(List<Map> input) {
        return input.stream().map(ticket -> {
            var ticketDocument = new TicketDocument();
            ticketDocument.setPrice((Float) ticket.get("price"));
            ticketDocument.setDateOfIssue((LocalDate) ticket.get("date"));
            ticketDocument.setCustomerId((Integer) ticket.get("customerId"));
            ticketDocument.setId(new TicketId(
                    (Integer) ticket.get("seatId"),
                    (Integer) ticket.get("moviehallId"),
                    (Integer) ticket.get("screeningId")
            ));
            return ticketDocument;
        }).collect(Collectors.toList());
    }
}
