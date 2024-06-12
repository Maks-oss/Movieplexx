package com.example.backend.migration.task;

import com.example.backend.data.nosql.CinemaDocument;
import com.example.backend.data.nosql.CustomerDocument;
import com.example.backend.data.nosql.MovieDocument;
import com.example.backend.data.sql.Cinema;
import com.example.backend.data.sql.Customer;
import com.example.backend.data.sql.Movie;
import com.example.backend.migration.transform.reader.CinemaReader;
import com.example.backend.migration.transform.reader.CustomerReader;
import com.example.backend.migration.transform.reader.MovieReader;
import com.example.backend.migration.transform.translator.CinemaTranslator;
import com.example.backend.migration.transform.translator.CustomerTranslator;
import com.example.backend.migration.transform.translator.MovieTranslator;
import com.example.backend.migration.transform.writer.MongoItemWriter;
import com.example.backend.migration.transform.writer.MovieItemWriter;
import com.example.backend.ApplicationContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unchecked")
public class MigrationTaskFactory {
    @Autowired
    private ApplicationContext applicationContext;
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
                applicationContext.getBean("movie_writer", MovieItemWriter.class)
        );
    }
}
