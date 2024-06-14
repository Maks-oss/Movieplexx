package com.example.backend.implementation.director;

import com.example.backend.data.nosql.DirectorDocument;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("directors")
public class DirectorController {
    private final DirectorRepository directorRepository;
    private final MongoTemplate mongoTemplate;

    public DirectorController(DirectorRepository directorRepository, MongoTemplate mongoTemplate) {
        this.directorRepository = directorRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/sql")
    public ResponseEntity<?> getDirectorsSql() {
        var directors = directorRepository.findAll();
        return ResponseEntity.ok(directors);
    }
    @GetMapping("/nosql")
    public ResponseEntity<?> getDirectorsNoSql() {
        return ResponseEntity.ok(mongoTemplate.findAll(DirectorDocument.class));
    }

    @GetMapping("/sql/{id}")
    public ResponseEntity<?> getDirectorByIdSql(@PathVariable int id) {
        var director = mongoTemplate.findById(id,DirectorDocument.class);
        if (director == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                new DirectorResponse(director.getId(), director.getFirstname(), director.getLastname())
        );
    }
}
