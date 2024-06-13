package com.example.backend.implementation.movie.sql;

import com.example.backend.data.sql.MovieHall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieHallRepository extends JpaRepository<MovieHall,Integer> {
}
