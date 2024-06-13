package com.example.backend.implementation.movie.nosql;

import com.example.backend.data.nosql.MovieScreeningDocument;
import com.example.backend.data.sql.MovieScreening;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MovieScreeningDocumentRepository extends MongoRepository<MovieScreeningDocument, Integer> {

    @Query(value = "{}",fields = "{'moviehall.number': 1}")
    List<MovieScreeningDocument> findMovieScreeningDocumentsByMovieId(int movieId);
}
