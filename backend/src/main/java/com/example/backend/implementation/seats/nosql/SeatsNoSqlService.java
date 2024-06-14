package com.example.backend.implementation.seats.nosql;

import com.example.backend.data.nosql.MovieHallDocument;
import com.example.backend.data.nosql.TicketDocument;
import com.example.backend.data.sql.Seat;
import com.example.backend.implementation.movie.sql.MovieHallRepository;
import com.example.backend.implementation.seats.SeatResponse;
import com.example.backend.implementation.seats.sql.SeatsRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class SeatsNoSqlService {
    private final MongoTemplate mongoTemplate;
    public SeatsNoSqlService( MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<SeatResponse> findSeatsForScreeningAndHall(int hallId, int screeningId) {
        var seats = mongoTemplate.findById(hallId, MovieHallDocument.class).getSeatList();
        seats.sort(Comparator.comparingInt(Seat::getNumber));
        var ticketQuery = new Query(Criteria.where("_id.screeningId").is(screeningId)
                .and("_id.hallId").is(hallId));
        var ticketsSeatIds = mongoTemplate.find(ticketQuery, TicketDocument.class).stream().map(t -> t.getId().getSeatId()).collect(Collectors.toList());
        return seats.stream()
                .map(seat -> new SeatResponse(
                                seat.getSeatId(),
                                seat.getMovieHall().getId(),
                                seat.getRow(),
                                seat.getType(),
                                seat.getNumber(),
                                seat.getPrice(),
                                ticketsSeatIds.contains(seat.getSeatId())
                        )
                )
                .collect(Collectors.toList());
    }
}
