package com.example.backend.migration.transform.translator;

import com.example.backend.data.nosql.ActorDocument;
import com.example.backend.data.nosql.DirectorDocument;
import com.example.backend.data.nosql.MovieDocument;
import com.example.backend.data.sql.Actor;
import com.example.backend.data.sql.Director;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("director_translator")
public class DirectorTranslator implements ItemTranslator<Director, DirectorDocument> {
    @Override
    @Transactional
    public List<DirectorDocument> transformData(List<Director> input) {
        return input.stream().map(director -> {
            var directorDocument = createDirectorDocument(director);
            directorDocument.setMovies(director.getMovies().stream().map(movie -> {
                var movieDocument = new MovieDocument();
                BeanUtils.copyProperties(movie, movieDocument);
                return movieDocument;
            }).collect(Collectors.toList()));
            return directorDocument;
        }).collect(Collectors.toList());
    }

    private DirectorDocument createDirectorDocument(Director director) {
        var directorDocument = new DirectorDocument();
        BeanUtils.copyProperties(director, directorDocument);
        return directorDocument;
    }
}
