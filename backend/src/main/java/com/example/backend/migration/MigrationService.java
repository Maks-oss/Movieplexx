package com.example.backend.migration;

import com.example.backend.migration.task.MigrationTaskFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.*;

@Service
public class MigrationService {
    private final MigrationTaskFactory migrationTaskFactory;

    public MigrationService(MigrationTaskFactory migrationTaskFactory) {
        this.migrationTaskFactory = migrationTaskFactory;
    }

    public void migrate() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(migrationTaskFactory.createCustomerMigrationTask());
        executorService.submit(migrationTaskFactory.createCinemaMigrationTask());
        executorService.submit(migrationTaskFactory.createMovieMigrationTask());
        executorService.submit(migrationTaskFactory.createTicketMigrationTask());
        executorService.submit(migrationTaskFactory.createMovieScreeningMigrationTask());
        executorService.submit(migrationTaskFactory.createMovieHallMigrationTask());
        executorService.submit(migrationTaskFactory.createActorMigrationTask());
        executorService.submit(migrationTaskFactory.createDirectorMigrationTask());
        executorService.submit(migrationTaskFactory.createRoleMigrationTask());
        executorService.submit(migrationTaskFactory.createEmployeeMigrationTask());
        executorService.shutdown();

    }
}
