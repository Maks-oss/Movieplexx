package com.example.backend.migration.transform.translator;

import com.example.backend.data.nosql.ActorDocument;
import com.example.backend.data.nosql.DirectorDocument;
import com.example.backend.data.nosql.MovieDocument;
import com.example.backend.data.sql.Actor;
import com.example.backend.data.sql.Director;
import com.example.backend.data.sql.Movie;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("director_translator")
public class DirectorTranslator implements ItemTranslator<Map, DirectorDocument> {
    @Override
    @Transactional
    public List<DirectorDocument> transformData(List<Map> input) {
        return input.stream().map(director -> {
            var directorDocument = createDirectorDocument(director);
            List<Movie> movies = (List<Movie>) director.get("movies");
            directorDocument.setMovies(movies.stream().map(movie -> {
                var movieDocument = new MovieDocument();
                BeanUtils.copyProperties(movie, movieDocument);
                return movieDocument;
            }).collect(Collectors.toList()));
            return directorDocument;
        }).collect(Collectors.toList());
    }

    private DirectorDocument createDirectorDocument(Map director) {
        var directorDocument = new DirectorDocument();
        directorDocument.setId((Integer) director.get("id"));
        directorDocument.setFirstname((String) director.get("firstName"));
        directorDocument.setLastname((String) director.get("lastName"));
        return directorDocument;
    }
}
