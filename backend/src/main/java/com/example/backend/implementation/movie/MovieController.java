package com.example.backend.implementation.movie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movies")
public class MovieController {
    private final MovieRepository movieRepository;
    private final MovieScreeningRepository movieScreeningRepository;

    public MovieController(MovieRepository movieRepository, MovieScreeningRepository movieScreeningRepository) {
        this.movieRepository = movieRepository;
        this.movieScreeningRepository = movieScreeningRepository;
    }

    @GetMapping
    public ResponseEntity<?> getMovieItems() {
        return ResponseEntity.ok(movieRepository.findAll().stream().map(movie ->
                new MovieItemDto(movie.getId(), movie.getName(), null, movie.getReleaseDate().toString())
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieDetails(@PathVariable int id){
        var screenings = movieScreeningRepository.findAll().stream().filter(screening -> screening.getMovie().getId() == id).findFirst();
        return screenings.isPresent() ? ResponseEntity.ok(screenings.get()) : ResponseEntity.notFound().build();
    }
}
