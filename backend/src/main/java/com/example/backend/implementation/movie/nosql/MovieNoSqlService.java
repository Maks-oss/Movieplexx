package com.example.backend.implementation.movie.nosql;

import com.example.backend.data.nosql.MovieDocument;
import com.example.backend.data.nosql.MovieScreeningDocument;
import com.example.backend.implementation.movie.MovieItemResponse;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class MovieNoSqlService {
    private MongoTemplate mongoTemplate;
    private MovieDocumentRepository movieDocumentRepository;

    private MovieScreeningDocumentRepository movieScreeningDocumentRepository;

    public MovieNoSqlService(MongoTemplate mongoTemplate, MovieDocumentRepository movieDocumentRepository, MovieScreeningDocumentRepository movieScreeningDocumentRepository) {
        this.mongoTemplate = mongoTemplate;
        this.movieDocumentRepository = movieDocumentRepository;
        this.movieScreeningDocumentRepository = movieScreeningDocumentRepository;
    }



    public List<MovieItemResponse> getAllMovieItems() {
        Query query = new Query();
        query.fields().exclude("actors","directors");
        return mongoTemplate.find(query, MovieDocument.class).stream().map(item ->
                new MovieItemResponse(item.getId(), item.getName(), item.getImage(), item.getReleaseDate().toString())
        ).collect(Collectors.toList());
    }

    public Map<String, Object> getMovieDetails(int movieId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(movieId));
        var movie = mongoTemplate.findOne(query, MovieDocument.class);
        Query screeningQuery = new Query();
        screeningQuery.addCriteria(Criteria.where("movieId").is(movieId));
        var screenings = mongoTemplate.find(screeningQuery, MovieScreeningDocument.class);
        if (movie == null) throw new NoSuchElementException("screenings or movie are absent");
        return Map.of(
                "movieInfo", movie,
                "movieScreenings", screenings,
                "movieCast", Map.of(
                        "actors", movie.getActors(),
                        "directors", movie.getDirectors()
                )
        );
    }
}
