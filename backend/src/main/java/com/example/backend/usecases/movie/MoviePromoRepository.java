package com.example.backend.usecases.movie;

import com.example.backend.data.Movie;
import com.example.backend.data.MoviePromo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MoviePromoRepository extends JpaRepository<MoviePromo, Integer> {
}
