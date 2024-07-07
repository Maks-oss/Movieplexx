package com.example.backend.repository;

import com.example.backend.data.persistence.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Integer> {
}
