package com.example.backend.generator;

import com.example.backend.migration.MigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class DataGeneratorController {
    @Autowired
    private DataGeneratorService generatorService;
//    @Autowired
//    private MigrationService migrationService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateData(UriComponentsBuilder uriComponentsBuilder) {
        generatorService.generateData();
        var uri = uriComponentsBuilder.path("/generate").query("type=YOUR_TYPE").build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping("/generate")
    public ResponseEntity<?> getGeneratedData(@RequestParam(defaultValue = "emp") String type) {
        return ResponseEntity.ok(generatorService.retrieveGeneratedData(type));
    }
//    @GetMapping("/migrate")
//    public String migrate() throws Exception {
////        migrationLogger.log(Level.INFO, "Start migration: " + toMigrate);
//        Thread.sleep(1000); // simulated delay
//        return migrationService.migrate();
//    }
}
