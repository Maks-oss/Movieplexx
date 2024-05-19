package com.example.backend.implementation.movie;

import com.example.backend.data.MovieScreening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieScreeningRepository extends JpaRepository<MovieScreening, Integer> {
}
