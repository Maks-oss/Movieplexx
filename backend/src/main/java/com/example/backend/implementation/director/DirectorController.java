package com.example.backend.implementation.director;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("directors")
public class DirectorController {
    private final DirectorRepository directorRepository;

    public DirectorController(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @GetMapping
    public ResponseEntity<?> getDirectors() {
        var directors = directorRepository.findAll();
        return ResponseEntity.ok(directors);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getDirectorById(@PathVariable int id) {
        var director = directorRepository.findById(id);
        if (!director.isPresent()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                Map.of(
                        "directorId", director.get().getId(),
                        "firstName", director.get().getFirstname(),
                        "lastName", director.get().getLastname()
                )
        );
    }
}
