package com.example.backend.controller;

import com.example.backend.data.request.MovieInsertRequest;
import com.example.backend.service.MovieSqlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("movies")
public class MovieController {
    private final MovieSqlService movieSqlService;

    public MovieController(MovieSqlService movieSqlService) {
        this.movieSqlService = movieSqlService;
    }

    @GetMapping
    public ResponseEntity<?> getMovieItemsSQL() {
        return ResponseEntity.ok(movieSqlService.getMovieItemsList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieDetailsSQL(@PathVariable int id) {
        return ResponseEntity.ok(movieSqlService.getMovieDetails(id));
    }

    @PostMapping
    public ResponseEntity<?> insertMovieSql(@RequestBody MovieInsertRequest movieToInsert, UriComponentsBuilder uriComponentsBuilder) {
        var insertedMovie = movieSqlService.insertMovie(movieToInsert);
        var uri = uriComponentsBuilder.path("/movies/" + insertedMovie.getId()).build().toUri();
        return ResponseEntity.created(uri).build();
    }
}
