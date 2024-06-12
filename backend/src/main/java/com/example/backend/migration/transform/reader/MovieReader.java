package com.example.backend.migration.transform.reader;

import com.example.backend.data.sql.Movie;
import com.example.backend.implementation.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component("movie_reader")
public class MovieReader extends SqlItemReader<Movie> {
    private final Logger logger = Logger.getLogger("Movie reader");

   @Autowired
   private MovieRepository movieRepository;
    @Override
    public List<Movie> readItems() {
//        return entityManager
//                .createQuery("""
//                             SELECT m FROM Movie m
//                             LEFT JOIN FETCH m.actors a
//                             LEFT JOIN FETCH m.directors d
//                             """, Movie.class)
//                .getResultList();
        List<Movie> all = movieRepository.findAll();
        return all;
    }
}
