package com.example.backend.implementation.actor;

import com.example.backend.data.sql.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
}
