package com.example.backend.controller;

import com.example.backend.repository.ActorRepository;
import com.example.backend.data.response.ActorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("actors")
public class ActorController {
    private final ActorRepository actorRepository;

    public ActorController(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @GetMapping
    public ResponseEntity<?> getActorsSql() {
        var actors = actorRepository.findAll();
        return ResponseEntity.ok(actors);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getActorByIdSql(@PathVariable int id) {
        var actor = actorRepository.findById(id);
        if (actor.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                new ActorResponse(actor.get().getId(), actor.get().getFirstname(), actor.get().getLastname())
        );
    }

}
