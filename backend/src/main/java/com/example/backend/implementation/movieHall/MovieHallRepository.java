package com.example.backend.implementation.movieHall;

import com.example.backend.data.MovieHall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieHallRepository extends JpaRepository<MovieHall,Integer> {
}
