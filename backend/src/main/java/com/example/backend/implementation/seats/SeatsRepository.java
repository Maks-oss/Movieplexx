package com.example.backend.implementation.seats;

import com.example.backend.data.MovieHall;
import com.example.backend.data.Seat;
import com.example.backend.data.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SeatsRepository extends JpaRepository<Seat, Integer> {

    @Query("SELECT s FROM Seat s WHERE s.movieHall.id=:movieHallId")
    List<Seat> findSeatsByMovieHallId(int movieHallId);

    List<Seat> findSeatsByTicket(Ticket ticket);

    @Modifying
    @Query("UPDATE Seat s SET s.ticket = NULL WHERE s.ticket.id = :ticketId")
    void dissociateTicket(String ticketId);
}
