package com.example.backend.implementation.actor;

import com.example.backend.data.nosql.ActorDocument;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("actors")
public class ActorController {
    private final ActorRepository actorRepository;
    private final MongoTemplate mongoTemplate;

    public ActorController(ActorRepository actorRepository, MongoTemplate mongoTemplate) {
        this.actorRepository = actorRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/sql")
    public ResponseEntity<?> getActorsSql() {
        var actors = actorRepository.findAll();
        return ResponseEntity.ok(actors);
    }

    @GetMapping("/nosql")
    public ResponseEntity<?> getActorsNoSql() {
//        var actors = actorRepository.findAll();
        return ResponseEntity.ok(mongoTemplate.findAll(ActorDocument.class));
    }

    @GetMapping("/sql/{id}")
    public ResponseEntity<?> getActorByIdSql(@PathVariable int id) {
        var actor = actorRepository.findById(id);
        if (actor.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                new ActorResponse(actor.get().getId(), actor.get().getFirstname(), actor.get().getLastname())
        );
    }

    @GetMapping("/nosql/{id}")
    public ResponseEntity<?> getActorByIdNoSql(@PathVariable int id) {
        var actor = mongoTemplate.findById(id, ActorDocument.class);
        if (actor == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                new ActorResponse(actor.getId(), actor.getFirstname(), actor.getLastname())
        );
    }
}
