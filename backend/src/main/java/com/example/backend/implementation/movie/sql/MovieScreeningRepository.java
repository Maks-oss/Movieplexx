package com.example.backend.implementation.movie.sql;

import com.example.backend.data.sql.MovieScreening;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieScreeningRepository extends JpaRepository<MovieScreening, Integer> {
    @Query("SELECT mv FROM MovieScreening mv JOIN Movie m on m.id = mv.movie.id WHERE m.id = :movieId")
    List<MovieScreening> findAllByMovieId(int movieId, Sort sort);
}
