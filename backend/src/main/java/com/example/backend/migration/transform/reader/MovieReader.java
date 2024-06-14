package com.example.backend.migration.transform.reader;

import com.example.backend.data.sql.Movie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component("movie_reader")
public class MovieReader extends SqlItemReader<Movie> {
    private final Logger logger = Logger.getLogger("Movie reader");
    @Override
    public List<Movie> readItems() {
        return entityManager
                .createQuery("""
                             SELECT m FROM Movie m
                             JOIN FETCH m.actors
                             JOIN FETCH m.directors
                             """, Movie.class)
                .getResultList();
    }
}
