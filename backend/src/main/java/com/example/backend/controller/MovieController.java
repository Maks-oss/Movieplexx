package com.example.backend.controller;

import com.example.backend.data.request.MovieInsertRequest;
import com.example.backend.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<?> getMovieItemsSQL() {
        return ResponseEntity.ok(movieService.getMovieItemsList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieDetailsSQL(@PathVariable int id) {
        return ResponseEntity.ok(movieService.getMovieDetails(id));
    }

    @PostMapping
    public ResponseEntity<?> insertMovieSql(@RequestBody MovieInsertRequest movieToInsert, UriComponentsBuilder uriComponentsBuilder) {
        var insertedMovie = movieService.insertMovie(movieToInsert);
        var uri = uriComponentsBuilder.path("/movies/" + insertedMovie.getId()).build().toUri();
        return ResponseEntity.created(uri).build();
    }
}
