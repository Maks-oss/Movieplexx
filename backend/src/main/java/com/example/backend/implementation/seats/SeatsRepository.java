package com.example.backend.implementation.seats;

import com.example.backend.data.Seat;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatsRepository extends JpaRepository<Seat, Integer> {

    @Query("SELECT s  FROM Seat s WHERE s.movieHall.id=:movieHallId")
    List<Seat> findSeatsByMovieHallId(int movieHallId, Sort sort);

    @Query("""
            SELECT s FROM Seat s INNER JOIN Ticket t on t.seat = s WHERE t.screening.id = :screeningId
            """)
    List<Seat> findSeatsByScreening(int screeningId);

}
