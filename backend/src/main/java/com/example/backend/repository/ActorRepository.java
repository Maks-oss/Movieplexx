package com.example.backend.repository;

import com.example.backend.data.persistence.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
}
