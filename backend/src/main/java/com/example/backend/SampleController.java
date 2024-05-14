package com.example.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SampleController {
    @Autowired
    private TestRepo testRepo;
    @GetMapping("/")
    public ResponseEntity<?> greeting() {
        testRepo.save(new TestObj("name 165"));
        return ResponseEntity.ok(testRepo.findAll());
    }
    @GetMapping("/clear")
    public ResponseEntity<?> clear() {
        testRepo.deleteAll();
        return ResponseEntity.ok("Deleted");
    }
}
