package com.example.backend.implementation.actor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("actors")
public class ActorController {
    private final ActorRepository actorRepository;

    public ActorController(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @GetMapping
    public ResponseEntity<?> getActors() {
        var actors = actorRepository.findAll();
        return ResponseEntity.ok(actors);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getActorById(@PathVariable int id) {
        var actor = actorRepository.findById(id);
        if (!actor.isPresent()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                Map.of(
                        "actorId", actor.get().getId(),
                        "firstName", actor.get().getFirstname(),
                        "lastName", actor.get().getLastname()
                )
        );
    }
}
