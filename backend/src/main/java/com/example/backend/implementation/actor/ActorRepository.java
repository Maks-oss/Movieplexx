package com.example.backend.implementation.actor;

import com.example.backend.data.Actor;
import com.example.backend.data.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
}
