package com.example.backend.migration.task;

import com.example.backend.data.nosql.*;
import com.example.backend.data.sql.*;
import com.example.backend.migration.transform.reader.*;
import com.example.backend.migration.transform.translator.*;
import com.example.backend.migration.transform.writer.MongoItemWriter;
import com.example.backend.migration.transform.writer.MovieItemWriter;
import com.example.backend.ApplicationContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
@SuppressWarnings("unchecked")
public class MigrationTaskFactory {
    private ApplicationContext applicationContext;

    public MigrationTaskFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public MigrationTask<Customer, CustomerDocument> createCustomerMigrationTask() {
        return new MigrationTask<>(
                applicationContext.getBean("customer_reader", CustomerReader.class),
                applicationContext.getBean("customer_translator", CustomerTranslator.class),
                (MongoItemWriter<CustomerDocument>) applicationContext.getBean("mongo_writer")
        );
    }
    public MigrationTask<Cinema, CinemaDocument> createCinemaMigrationTask() {
        return new MigrationTask<>(
                applicationContext.getBean("cinema_reader", CinemaReader.class),
                applicationContext.getBean("cinema_translator", CinemaTranslator.class),
                (MongoItemWriter<CinemaDocument>) applicationContext.getBean("mongo_writer")
        );
    }

    public MigrationTask<Movie, MovieDocument> createMovieMigrationTask() {
        return new MigrationTask<>(
                applicationContext.getBean("movie_reader", MovieReader.class),
                applicationContext.getBean("movie_translator", MovieTranslator.class),
                (MongoItemWriter<MovieDocument>) applicationContext.getBean("mongo_writer")
        );
    }

    public MigrationTask<Map, TicketDocument> createTicketMigrationTask() {
        return new MigrationTask<>(
                applicationContext.getBean("ticket_reader", TicketReader.class),
                applicationContext.getBean("ticket_translator", TicketTranslator.class),
                (MongoItemWriter<TicketDocument>) applicationContext.getBean("mongo_writer")
        );
    }

    public MigrationTask<Map, MovieScreeningDocument> createMovieScreeningMigrationTask() {
        return new MigrationTask<>(
                applicationContext.getBean("movie_screening_reader", MovieScreeningReader.class),
                applicationContext.getBean("movie_screening_translator", MovieScreeningTranslator.class),
                (MongoItemWriter<MovieScreeningDocument>) applicationContext.getBean("mongo_writer")
        );
    }

    public MigrationTask<Map<String, Object>, MovieHallDocument> createMovieHallMigrationTask() {
        return new MigrationTask<>(
                applicationContext.getBean("hall_reader", MovieHallReader.class),
                applicationContext.getBean("hall_translator", MovieHallTranslator.class),
                (MongoItemWriter<MovieHallDocument>) applicationContext.getBean("mongo_writer")
        );
    }
    public MigrationTask<Actor, ActorDocument> createActorMigrationTask() {
        return new MigrationTask<>(
                applicationContext.getBean("actor_reader", ActorReader.class),
                applicationContext.getBean("actor_translator", ActorTranslator.class),
                (MongoItemWriter<ActorDocument>) applicationContext.getBean("mongo_writer")
        );
    }
    public MigrationTask<Director, DirectorDocument> createDirectorMigrationTask() {
        return new MigrationTask<>(
                applicationContext.getBean("director_reader", DirectorReader.class),
                applicationContext.getBean("director_translator", DirectorTranslator.class),
                (MongoItemWriter<DirectorDocument>) applicationContext.getBean("mongo_writer")
        );
    }

    public MigrationTask<Employee, EmployeeDocument> createEmployeeMigrationTask() {
        return new MigrationTask<>(
                applicationContext.getBean("employee_reader", EmployeeReader.class),
                applicationContext.getBean("employee_translator", EmployeeTranslator.class),
                (MongoItemWriter<EmployeeDocument>) applicationContext.getBean("mongo_writer")
        );
    }
    public MigrationTask<Role, RoleDocument> createRoleMigrationTask() {
        return new MigrationTask<>(
                applicationContext.getBean("role_reader", RoleReader.class),
                applicationContext.getBean("role_translator", RoleTranslator.class),
                (MongoItemWriter<RoleDocument>) applicationContext.getBean("mongo_writer")
        );
    }
}
