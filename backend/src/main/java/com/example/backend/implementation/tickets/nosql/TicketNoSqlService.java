package com.example.backend.implementation.tickets.nosql;

import com.example.backend.data.nosql.MovieHallDocument;
import com.example.backend.data.nosql.TicketDocument;
import com.example.backend.data.nosql.TicketId;
import com.example.backend.data.sql.Seat;
import com.example.backend.implementation.tickets.CreateTicketRequestBody;
import com.example.backend.implementation.tickets.TicketResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketNoSqlService {
    private final MongoTemplate mongoTemplate;

    public TicketNoSqlService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Transactional
    public TicketResponse createTicketResponse(CreateTicketRequestBody createTicketRequestBody) {
        List<Integer> seatId = createTicketRequestBody.seatId();
        var preferredSeat = getPreferredSeat(seatId.get(0), seatId.get(1), createTicketRequestBody.movieScreening().getId());
        var ticket = createTicket(createTicketRequestBody, preferredSeat);
        var screening = createTicketRequestBody.movieScreening();
        var movieHall = screening.getMoviehall();
        var cinema = movieHall.getCinema();
        var movie = screening.getMovie();
        return new TicketResponse(
                movie.getName(),
                ticket.getPrice(),
                ticket.getDateOfIssue(),
                movieHall.getType() + " " + movieHall.getNumber(),
                cinema.getName(),
                screening.getStartTime().toString(),
                preferredSeat.getRow() + " " + preferredSeat.getNumber()
        );
    }

    @Transactional
    public void clearAll() {
        mongoTemplate.remove(TicketDocument.class);
    }

    private TicketDocument createTicket(CreateTicketRequestBody createTicketRequestBody, Seat seat) {
        var ticket = new TicketDocument();
        ticket.setDateOfIssue(LocalDate.now());
        if (createTicketRequestBody.isEmployee()) {
            ticket.setEmployeeId(createTicketRequestBody.userId());
        } else {
            ticket.setCustomerId(createTicketRequestBody.userId());
        }
        if (seat == null) throw new RuntimeException("Invalid seat");
        ticket.setId(new TicketId(seat.getSeatId(), seat.getMovieHall().getId(), createTicketRequestBody.movieScreening().getId()));
        ticket.setPrice(seat.getPrice());
        return mongoTemplate.save(ticket);
    }

    private Seat getPreferredSeat(int seatId, int movieHallId, int screeningId) {
        if (isSeatOccupied(seatId, movieHallId, screeningId))
            throw new IllegalArgumentException("Seat is already occupied");
        var seats = mongoTemplate.findById(movieHallId, MovieHallDocument.class).getSeatList();
        for (var seat : seats) {
            if (seat.getSeatId() == seatId) {
                return seat;
            }
        }
        return null;
    }

    private boolean isSeatOccupied(int seatId, int movieHallId, int screeningId) {
        var ticketQuery = new Query(Criteria.where("_id.screeningId").is(screeningId)
                .and("_id.hallId").is(movieHallId));
        var ticketsSeatIds = mongoTemplate.find(ticketQuery, TicketDocument.class).stream().map(t -> t.getId().getSeatId()).collect(Collectors.toList());
        for (var seat : ticketsSeatIds) {
            if (seat == seatId) {
                return true;
            }
        }
        return false;
    }

}
