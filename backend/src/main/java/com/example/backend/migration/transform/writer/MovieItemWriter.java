package com.example.backend.migration.transform.writer;

import com.example.backend.data.nosql.MovieDocument;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component("movie_writer")
public class MovieItemWriter extends MongoItemWriter<MovieDocument>{
    private Logger logger = Logger.getLogger("Movie writer");
    @Override
    public void writeData(List<MovieDocument> data) {
        data.forEach(movieDocument -> {
            movieDocument.getActors().forEach(actorDocument -> {
                mongoTemplate.save(actorDocument);
            });
            movieDocument.getDirectors().forEach(directorDocument -> {
                mongoTemplate.save(directorDocument);
            });
            mongoTemplate.save(movieDocument);
        });
    }
}
