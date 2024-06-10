package com.example.backend.implementation.movie;

import com.example.backend.data.MovieScreening;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieScreeningRepository extends JpaRepository<MovieScreening, Integer> {
    List<MovieScreening> findAllByMovieId(int movieId, Sort sort);
}
