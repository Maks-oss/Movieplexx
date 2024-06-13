package com.example.backend.migration.transform.writer;

import com.example.backend.data.nosql.MovieDocument;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component("movie_writer")
public class MovieItemWriter extends MongoItemWriter<MovieDocument>{
    @Override
    public void writeData(List<MovieDocument> data) {
        data.forEach(movieDocument -> {
            mongoTemplate.save(movieDocument);
        });
    }
}
