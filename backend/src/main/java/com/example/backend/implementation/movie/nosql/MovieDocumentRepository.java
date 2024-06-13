package com.example.backend.implementation.movie.nosql;

import com.example.backend.data.nosql.MovieDocument;
import com.example.backend.data.sql.Actor;
import com.example.backend.data.sql.Director;
import com.example.backend.data.sql.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieDocumentRepository extends MongoRepository<MovieDocument, Integer> {

//    @Query("SELECT a FROM Movie m INNER JOIN Actor a on a.movie.id = m.id WHERE m.id = :movieId")
//    @Query("SELECT a FROM Actor a JOIN a.movies m WHERE m.id = :movieId")
//    List<Actor> findMovieActors(@Param("movieId") int movieId);
    @Query(value = "{ '_id': ?0 }", fields = "{ 'actors': 1,'actors.movies' : 0, 'directors': 1,'directors.movies' : 0 }")
    MovieDocument findMovieDocumentById(int movieId);
//    @Query("SELECT d FROM Movie m INNER JOIN Director d on d.movie.id = m.id WHERE m.id = :movieId")
//    @Query("SELECT d FROM Director d JOIN d.movies m WHERE m.id = :movieId")
//    List<Director> findMovieDirectors(@Param("movieId") int movieId);
}
