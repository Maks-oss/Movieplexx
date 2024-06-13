package com.example.backend.migration.transform.reader;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("movie_screening_reader")
public class MovieScreeningReader extends SqlItemReader<Map> {
    @Override
    public List<Map> readItems() {
        return entityManager.createQuery("""
                SELECT 
                m.id AS id, 
                m.startTime AS startTime, 
                m.endTime AS endTime, 
                m.moviehall AS movieHall,
                m.movie.id AS movieId
                FROM MovieScreening m
                """, Map.class).getResultList();
    }
}
