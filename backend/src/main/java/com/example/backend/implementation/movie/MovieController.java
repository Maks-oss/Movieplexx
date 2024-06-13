package com.example.backend.implementation.movie;

import com.example.backend.implementation.movie.nosql.MovieNoSqlService;
import com.example.backend.implementation.movie.sql.MovieSqlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("movies")
public class MovieController {
    private final MovieSqlService movieSqlService;
    private final MovieNoSqlService movieNoSqlService;

    public MovieController(MovieSqlService movieSqlService, MovieNoSqlService movieNoSqlService) {
        this.movieSqlService = movieSqlService;
        this.movieNoSqlService = movieNoSqlService;
    }

    @GetMapping("/nosql")
    public ResponseEntity<?> getMovieItemsNoSQl() {
        return ResponseEntity.ok(movieNoSqlService.getAllMovieItems());
    }
    @GetMapping("/sql")
    public ResponseEntity<?> getMovieItemsSQL() {
        return ResponseEntity.ok(movieSqlService.getMovieItemsList());
    }

    @GetMapping("/sql/{id}")
    public ResponseEntity<?> getMovieDetailsSQL(@PathVariable int id) {
        return ResponseEntity.ok(movieSqlService.getMovieDetails(id));
    }
    @GetMapping("/nosql/{id}")
    public ResponseEntity<?> getMovieDetailsNoSQL(@PathVariable int id) {
        return ResponseEntity.ok(movieNoSqlService.getMovieDetails(id));
    }

    @PostMapping
    public ResponseEntity<?> insertMovie(@RequestBody MovieInsertRequest movieToInsert, UriComponentsBuilder uriComponentsBuilder) {
        var insertedMovie = movieSqlService.insertMovie(movieToInsert);
        var uri = uriComponentsBuilder.path("/movies/" + insertedMovie.getId()).build().toUri();
        return ResponseEntity.created(uri).build();
    }
}
