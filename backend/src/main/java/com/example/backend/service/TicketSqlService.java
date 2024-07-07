package com.example.backend.service;

import com.example.backend.data.persistence.Seat;
import com.example.backend.data.persistence.Ticket;
import com.example.backend.data.request.TicketRequestBody;
import com.example.backend.repository.CustomerRepository;
import com.example.backend.repository.EmployeeRepository;
import com.example.backend.repository.SeatsRepository;
import com.example.backend.data.response.TicketResponse;
import com.example.backend.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TicketSqlService {
    private final TicketRepository ticketRepository;
    private final SeatsRepository seatsRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customer;

    public TicketSqlService(TicketRepository ticketRepository, SeatsRepository seatsRepository, EmployeeRepository employeeRepository, CustomerRepository customer) {
        this.ticketRepository = ticketRepository;
        this.seatsRepository = seatsRepository;
        this.employeeRepository = employeeRepository;
        this.customer = customer;
    }

    @Transactional
    public TicketResponse createTicketResponse(TicketRequestBody ticketRequestBody) {
        var ticket = createTicket(ticketRequestBody);
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

    private Ticket createTicket(TicketRequestBody ticketRequestBody) {
        var ticket = new Ticket();
        ticket.setScreening(ticketRequestBody.movieScreening());
        if (ticketRequestBody.isEmployee()) {
            ticket.setEmployee(employeeRepository.findById(ticketRequestBody.userId()).orElseThrow());
        } else {
            ticket.setCustomer(customer.findById(ticketRequestBody.userId()).orElseThrow());
        }
        ticket.setDateOfIssue(LocalDate.now());
        List<Integer> seatId = ticketRequestBody.seatId();
        var seat = getPreferredSeat(seatId.get(0), seatId.get(1), ticketRequestBody.movieScreening().getId());
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
