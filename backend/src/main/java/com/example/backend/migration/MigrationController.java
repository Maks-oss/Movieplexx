package com.example.backend.migration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MigrationController {
    private final MigrationService migrationService;

    public MigrationController(MigrationService migrationService) {
        this.migrationService = migrationService;
    }

    @GetMapping("/migrate")
    public ResponseEntity<?> migrate() {

        migrationService.migrate();
        return ResponseEntity.noContent().build();

    }
}
