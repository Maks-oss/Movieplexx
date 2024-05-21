package com.example.backend.implementation.tickets;

import com.example.backend.data.Seat;
import com.example.backend.data.Ticket;
import com.example.backend.implementation.seats.SeatsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private TicketRepository ticketRepository;
    private SeatsRepository seatsRepository;
    private final Logger ticketLogger = Logger.getLogger("ticket");

    public TicketService(TicketRepository ticketRepository, SeatsRepository seatsRepository) {
        this.ticketRepository = ticketRepository;
        this.seatsRepository = seatsRepository;
    }
    @Transactional
    public TicketResponse createTicketResponse(CreateTicketRequestBody createTicketRequestBody) {
        var ticket = createTicket(createTicketRequestBody);
        var ticketSeats = seatsRepository.findSeatsByTicket(ticket);
        var movieHall = ticketSeats.get(0).getMovieHall();
        var cinema = movieHall.getCinema();
        var screening = ticket.getScreening();
        return new TicketResponse(
                ticket.getId(),
                screening.getMovie().getName(),
                ticket.getPrice(),
                ticket.getDateOfIssue(),
                movieHall.getType() + " " + movieHall.getNumber(),
                cinema.getName(),
                screening.getStartTime().toString(),
                ticketSeats.stream().map(seat -> seat.getRow() + " " + seat.getNumber()).collect(Collectors.toList())
        );
    }

    public List<Ticket> getCustomerTickets(int customerId) {
        return ticketRepository.findTicketsByCustomerId(customerId);
    }

    @Transactional
    public void clearAll() {
        ticketRepository.findAll().forEach(t -> seatsRepository.dissociateTicket(t.getId()));
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
        var seats = getPreferredSeats(createTicketRequestBody.seatsIds(), ticket.getScreening().getMoviehall().getId());
        ticket.setPrice((float) seats.stream().mapToDouble(Seat::getPrice).sum());
        Ticket saved = ticketRepository.save(ticket);
        reserveSeats(seats, saved);
        return saved;
    }
    private void reserveSeats(List<Seat> seats, Ticket ticket) {
        for (var seat: seats) {
            seat.setTicket(ticket);
        }
    }
    private List<Seat> getPreferredSeats(List<Integer> seatsIds, int movieHallId) {
        var seats = seatsRepository.findSeatsByMovieHallId(movieHallId);
        var occupiedSeats = new ArrayList<Seat>();
        for (var seat: seats) {
            if (seatsIds.contains(seat.getId())) {
                occupiedSeats.add(seat);
            }
        }
        return occupiedSeats;
    }

}
