package com.example.backend.implementation.seats;

import com.example.backend.data.Seat;
import com.example.backend.data.Ticket;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatsRepository extends JpaRepository<Seat, Integer> {

    @Query("SELECT s FROM Seat s WHERE s.movieHall.id=:movieHallId")
    List<Seat> findSeatsByMovieHallId(int movieHallId, Sort sort);

    List<Seat> findSeatsByTicket(Ticket ticket);

    @Modifying
    @Query("UPDATE Seat s SET s.ticket = NULL WHERE s.ticket.id = :ticketId")
    void dissociateTicket(String ticketId);
}
