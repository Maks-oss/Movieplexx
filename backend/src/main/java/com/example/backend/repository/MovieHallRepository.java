package com.example.backend.repository;

import com.example.backend.data.persistence.MovieHall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieHallRepository extends JpaRepository<MovieHall,Integer> {
}
