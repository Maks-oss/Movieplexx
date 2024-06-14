package com.example.backend.migration.transform.translator;

import com.example.backend.data.nosql.ActorDocument;
import com.example.backend.data.nosql.DirectorDocument;
import com.example.backend.data.nosql.EmployeeDocument;
import com.example.backend.data.nosql.MovieDocument;
import com.example.backend.data.sql.Movie;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component("movie_translator")
public class MovieTranslator implements ItemTranslator<Movie, MovieDocument> {
    @Override
    @Transactional
    public List<MovieDocument> transformData(List<Movie> input) {
        return input.stream().map(movie -> {
            var movieDocument = createMovieDocument(movie);
            movieDocument.setActors(movie.getActors().stream().map(actor -> {
                var actorDocument = new ActorDocument();
                BeanUtils.copyProperties(actor, actorDocument);
                return actorDocument;
            }).collect(Collectors.toList()));
            movieDocument.setDirectors(movie.getDirectors().stream().map(director -> {
                var directorDocument = new DirectorDocument();
                BeanUtils.copyProperties(director, directorDocument);
                return directorDocument;
            }).collect(Collectors.toList()));
            movieDocument.setEmployee(createEmployeeDocument(movie));
            return movieDocument;
        }).collect(Collectors.toList());
    }

    private MovieDocument createMovieDocument(Movie movie) {
        var movieDocument = new MovieDocument();
        BeanUtils.copyProperties(movie, movieDocument);
        return movieDocument;
    }
    private EmployeeDocument createEmployeeDocument(Movie movie) {
        var employee = new EmployeeDocument();
        BeanUtils.copyProperties(movie.getManager(), employee);
        return employee;
    }
}
