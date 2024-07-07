package com.example.backend.repository;

import com.example.backend.data.persistence.Actor;
import com.example.backend.data.persistence.Director;
import com.example.backend.data.persistence.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

//    @Query("SELECT a FROM Movie m INNER JOIN Actor a on a.movie.id = m.id WHERE m.id = :movieId")
    @Query("SELECT a FROM Actor a JOIN a.movies m WHERE m.id = :movieId")
    List<Actor> findMovieActors(@Param("movieId") int movieId);

//    @Query("SELECT d FROM Movie m INNER JOIN Director d on d.movie.id = m.id WHERE m.id = :movieId")
    @Query("SELECT d FROM Director d JOIN d.movies m WHERE m.id = :movieId")
    List<Director> findMovieDirectors(@Param("movieId") int movieId);
}
