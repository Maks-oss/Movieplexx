package com.example.backend.implementation.director;

import com.example.backend.data.sql.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Integer> {
}
