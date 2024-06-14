package com.example.backend.migration.transform.reader;

import com.example.backend.data.sql.Actor;
import com.example.backend.data.sql.Director;
import com.example.backend.data.sql.Employee;
import com.example.backend.data.sql.Movie;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("director_reader")
@SuppressWarnings("unchecked")
public class DirectorReader extends SqlItemReader<Map> {
    @Override
    public List<Map> readItems() {
        List<Map> directors = entityManager
                .createQuery("""
                        SELECT a.id as id, a.firstname as firstName, a.lastname as lastName FROM Director a
                        """, Map.class)
                .getResultList();
        fetchDirectorMovies(directors);
        return directors;
    }

    private void fetchDirectorMovies(List<Map> directors) {
        directors.forEach(director -> {
            List<Map> moviesMap = entityManager
                    .createQuery("""
                        SELECT 
                        m.id as id,
                        m.description as descr, 
                        m.ageRating as rat, 
                        m.releaseDate as rel,
                        m.name as name,
                        m.image as im,
                        m.manager as man
                        FROM Movie m
                        """, Map.class)
                    .getResultList();
            var movies = new ArrayList<Movie>();
            for (var map : moviesMap) {
                var movie = new Movie();
                movie.setId((Integer) map.get("id"));
                movie.setDescription((String) map.get("descr"));
                movie.setAgeRating((Integer) map.get("rat"));
                movie.setReleaseDate((LocalDate) map.get("rel"));
                movie.setName((String) map.get("name"));
                movie.setImage((String) map.get("im"));
                movie.setManager((Employee) map.get("man"));
                movies.add(movie);
            }
            director.put("movies", movies);
        });
    }
}
