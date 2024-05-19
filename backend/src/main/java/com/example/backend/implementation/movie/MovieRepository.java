package com.example.backend.implementation.movie;

import com.example.backend.data.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
