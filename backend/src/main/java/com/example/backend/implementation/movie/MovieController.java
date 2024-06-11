package com.example.backend.implementation.movie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<?> getMovieItems() {
        return ResponseEntity.ok(movieService.getMovieItemsList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieDetails(@PathVariable int id) {
        var screenings = movieService.getMovieScreenings(id);
        var movieCast = movieService.getMovieCast(id);
        if (screenings == null || movieCast.get("actors") == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                Map.of(
                        "movieInfo", screenings.get(0).getMovie(),
                        "movieScreenings", screenings,
                        "movieCast", movieCast
                )
        );
    }

    @PostMapping
    public ResponseEntity<?> insertMovie(@RequestBody MovieInsertRequest movieToInsert, UriComponentsBuilder uriComponentsBuilder) {
        var insertedMovie = movieService.insertMovie(movieToInsert);
        var uri = uriComponentsBuilder.path("/movies/" + insertedMovie.getId()).build().toUri();
        return ResponseEntity.created(uri).build();
    }
}
