package com.example.backend.implementation.movie;

import com.example.backend.data.Actor;
import com.example.backend.data.Director;
import com.example.backend.data.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    /*@Query(value = """
            SELECT new com.example.backend.implementation.movie.MovieCast(CONCAT(d.firstname, ' ', d.lastname),  CONCAT(a.firstname, ' ', a.lastname), m.name) FROM Actor a
            INNER JOIN Movie m on m.id = a.movie.id
            INNER JOIN Director d on d.movie.id = m.id
            WHERE m.id = :movieId
            """)
    List<MovieCast> findMovieCast(@Param("movieId") int movieId);*/

    @Query("SELECT a FROM Movie m INNER JOIN Actor a on a.movie.id = m.id WHERE m.id = :movieId")
    List<Actor> findMovieActors(@Param("movieId") int movieId);

    @Query("SELECT d FROM Movie m INNER JOIN Director d on d.movie.id = m.id WHERE m.id = :movieId")
    List<Director> findMovieDirectors(@Param("movieId") int movieId);
}
