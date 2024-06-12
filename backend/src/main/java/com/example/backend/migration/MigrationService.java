package com.example.backend.migration;

import com.example.backend.migration.task.MigrationTaskFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class MigrationService {
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final MigrationTaskFactory migrationTaskFactory;

    public MigrationService(MigrationTaskFactory migrationTaskFactory) {
        this.migrationTaskFactory = migrationTaskFactory;
    }

    public void migrate() {
        executorService.submit(migrationTaskFactory.createCustomerMigrationTask());
        executorService.submit(migrationTaskFactory.createCinemaMigrationTask());
        executorService.submit(migrationTaskFactory.createMovieMigrationTask());
        executorService.shutdown();
    }
}
