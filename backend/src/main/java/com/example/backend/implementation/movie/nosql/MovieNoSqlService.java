package com.example.backend.implementation.movie.nosql;

import com.example.backend.data.nosql.*;
import com.example.backend.data.sql.*;
import com.example.backend.generator.DataGeneratorService;
import com.example.backend.implementation.movie.MovieInsertRequest;
import com.example.backend.implementation.movie.MovieItemResponse;
import com.example.backend.utils.NoSqlCollectionMapper;
import net.datafaker.Faker;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class MovieNoSqlService {
    private MongoTemplate mongoTemplate;


    public MovieNoSqlService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public MovieDocument insertMovie(MovieInsertRequest movieInsertRequest) {
        MovieDocument movieDocument = new MovieDocument();
        movieDocument.setId(findLastMovieId() + 1);
        movieDocument.setName(movieInsertRequest.name());
        movieDocument.setDescription(movieInsertRequest.description());
        movieDocument.setImage(movieInsertRequest.image());
        movieDocument.setRuntime(movieInsertRequest.runTime());
        movieDocument.setReleaseDate(movieInsertRequest.releaseDate());
        movieDocument.setAgeRating(movieInsertRequest.ageRating());
        EmployeeDocument manager = mongoTemplate.findById(movieInsertRequest.managerId(), EmployeeDocument.class);
        movieDocument.setEmployee(manager);

        List<ActorDocument> actors = new ArrayList<>();

        for (Integer actorId : movieInsertRequest.actorIds()) {
            Query query = new Query(Criteria.where("_id").is(actorId));
            query.fields().exclude("movies");
            ActorDocument actor = mongoTemplate.findOne(query, ActorDocument.class);
            if (actor != null){
                actor.addMovie(movieDocument);
                actors.add(actor);
            }
        }
        movieDocument.setActors(actors);

        List<DirectorDocument> directors = new ArrayList<>();
        for (Integer directorId : movieInsertRequest.directorIds()) {
            Query query = new Query(Criteria.where("_id").is(directorId));
            query.fields().exclude("movies");
            DirectorDocument director = mongoTemplate.findOne(query, DirectorDocument.class);
            if (director != null){
                director.addMovie(movieDocument);
                directors.add(director);
            }
        }
        movieDocument.setDirectors(directors);

        mongoTemplate.save(movieDocument);

        var movieHalls = mongoTemplate.findAll(MovieHallDocument.class);
        for (int i = 0; i < movieHalls.size() / 3; i++) {
            generateMovieScreening(movieDocument, movieHalls.get(i));
        }
        return movieDocument;
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
        query.addCriteria(where("_id").is(movieId));
        var movie = mongoTemplate.findOne(query, MovieDocument.class);
        Query screeningQuery = new Query();
        screeningQuery.addCriteria(where("movieId").is(movieId));
        var screenings = mongoTemplate.find(screeningQuery, MovieScreeningDocument.class);
        screenings.forEach(sc -> sc.setMovie(movie));
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

    private Integer findLastMovieId() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "_id"));
        query.limit(1);
        return mongoTemplate.findOne(query, MovieDocument.class).getId();
    }
    private Integer findLastMovieScreeningId() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "_id"));
        query.limit(1);
        return mongoTemplate.findOne(query, MovieScreeningDocument.class).getId();
    }
    private void generateMovieScreening(MovieDocument movie, MovieHallDocument movieHall) {
        var movieScreeningDocument = new MovieScreeningDocument();
        Timestamp start = Timestamp.valueOf(new Faker().date().future(30, TimeUnit.DAYS).toLocalDateTime().withMinute(0).withSecond(0).withNano(0));
        Timestamp end = new Timestamp(start.getTime() + TimeUnit.MINUTES.toMillis(movie.getRuntime()));
        movieScreeningDocument.setId(findLastMovieScreeningId() + 1);
        movieScreeningDocument.setStartTime(start.toInstant());
        movieScreeningDocument.setEndTime(end.toInstant());
        movieScreeningDocument.setMovieId(movie.getId());
        movieScreeningDocument.setMoviehall(movieHall);
        mongoTemplate.save(movieScreeningDocument);
    }
}
