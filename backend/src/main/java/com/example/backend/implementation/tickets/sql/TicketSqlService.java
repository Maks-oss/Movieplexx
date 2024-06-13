package com.example.backend.implementation.tickets.sql;

import com.example.backend.data.sql.Seat;
import com.example.backend.data.sql.Ticket;
import com.example.backend.implementation.seats.sql.SeatsRepository;
import com.example.backend.implementation.tickets.CreateTicketRequestBody;
import com.example.backend.implementation.tickets.TicketResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Service
public class TicketSqlService {
    private final TicketRepository ticketRepository;
    private final SeatsRepository seatsRepository;
    private final Logger ticketLogger = Logger.getLogger("ticket");

    public TicketSqlService(TicketRepository ticketRepository, SeatsRepository seatsRepository) {
        this.ticketRepository = ticketRepository;
        this.seatsRepository = seatsRepository;
    }
    @Transactional
    public TicketResponse createTicketResponse(CreateTicketRequestBody createTicketRequestBody) {
        var ticket = createTicket(createTicketRequestBody);
        var seat = ticket.getSeat();
        var movieHall = seat.getMovieHall();
        var cinema = movieHall.getCinema();
        var screening = ticket.getScreening();
        var movie = screening.getMovie();
        return new TicketResponse(
                movie.getName(),
                ticket.getPrice(),
                ticket.getDateOfIssue(),
                movieHall.getType() + " " + movieHall.getNumber(),
                cinema.getName(),
                screening.getStartTime().toString(),
                seat.getRow() + " " + seat.getNumber()
        );
    }

    public List<Ticket> getCustomerTickets(int customerId) {
        return ticketRepository.findTicketsByCustomerId(customerId);
    }

    @Transactional
    public void clearAll() {
        ticketRepository.deleteAll();
    }

    private Ticket createTicket(CreateTicketRequestBody createTicketRequestBody) {
        var ticket = new Ticket();
        ticket.setScreening(createTicketRequestBody.movieScreening());
        if (createTicketRequestBody.customer() != null) {
            ticket.setCustomer(createTicketRequestBody.customer());
        }
        if (createTicketRequestBody.employee() != null) {
            ticket.setEmployee(createTicketRequestBody.employee());
        }
        ticket.setDateOfIssue(LocalDate.now());
        List<Integer> seatId = createTicketRequestBody.seatId();
        var seat = getPreferredSeat(seatId.get(0), seatId.get(1), createTicketRequestBody.movieScreening().getId());
        if (seat == null) throw new RuntimeException("Invalid seat");
        ticket.setPrice(seat.getPrice());
        ticket.setSeat(seat);
        return ticketRepository.save(ticket);
    }
    private Seat getPreferredSeat(int seatId, int movieHallId, int screeningId) {
        if (isSeatOccupied(seatId, movieHallId,screeningId)) throw new IllegalArgumentException("Seat is already occupied");
        var seats = seatsRepository.findSeatsByMovieHallId(movieHallId, Sort.unsorted());
        for (var seat: seats) {
            if (seat.getSeatId() == seatId) {
                return seat;
            }
        }
        return null;
    }
    private boolean isSeatOccupied(int seatId, int movieHallId, int screeningId) {
        var occupiedSeats = seatsRepository.findSeatsByScreening(screeningId, movieHallId);
        for (var seat: occupiedSeats) {
            if (seat.getSeatId() == seatId) {
                return true;
            }
        }
        return false;
    }

}
