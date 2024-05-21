package com.example.backend.implementation.tickets;

import com.example.backend.data.Seat;
import com.example.backend.data.Ticket;
import com.example.backend.implementation.seats.SeatsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private TicketRepository ticketRepository;
    private SeatsRepository seatsRepository;

    public TicketService(TicketRepository ticketRepository, SeatsRepository seatsRepository) {
        this.ticketRepository = ticketRepository;
        this.seatsRepository = seatsRepository;
    }
    public TicketResponse createTicketResponse(TicketGenerationData ticketGenerationData) {
        var ticket = createTicket(ticketGenerationData);
//        ticketGenerationData.movieScreening().getMoviehall().getId();
        var reservedSeats = reserveSeats(ticketGenerationData.seatsIds(), ticket);
        ticket.setPrice(calculateTicketPrice(reservedSeats));
        var movieHall = reservedSeats.get(0).getMovieHall();
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
                reservedSeats.stream().map(seat -> seat.getRow() + " " + seat.getNumber()).collect(Collectors.toList())
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
    private Ticket createTicket(TicketGenerationData ticketGenerationData) {
        var ticket = new Ticket();
        ticket.setScreening(ticketGenerationData.movieScreening());
        if (ticketGenerationData.customer() != null) {
            ticket.setCustomer(ticketGenerationData.customer());
        }
        if (ticketGenerationData.employee() != null) {
            ticket.setEmployee(ticketGenerationData.employee());
        }
//        ticket.setPrice(ticketGenerationData.ticketPrice());
        ticket.setDateOfIssue(LocalDate.now());
        return ticketRepository.save(ticket);
    }
    private List<Seat> reserveSeats(List<Integer> seatsIds, Ticket ticket) {
        var seats = seatsRepository.findSeatsByMovieHallId(ticket.getScreening().getMoviehall().getId());
        for (var seat: seats) {
            if (seatsIds.contains(seat.getId())) {
                seat.setTicket(ticket);
            }
        }
        seatsRepository.saveAll(seats);
        return seatsRepository.findSeatsByTicket(ticket);
    }

    private float calculateTicketPrice(List<Seat> reservedSeats) {
        // TODO change generation to include seat price
        return /*(float) reservedSeats.stream().mapToDouble(Seat::getPrice).sum()*/ 0f;
    }

}
