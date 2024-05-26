package com.example.backend.implementation.movie;

import com.example.backend.data.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("movies")
public class MovieController {
    private final MovieRepository movieRepository;
    private final MovieScreeningRepository movieScreeningRepository;
    private final MovieService movieService;

    public MovieController(MovieRepository movieRepository, MovieScreeningRepository movieScreeningRepository,MovieService movieService) {
        this.movieRepository = movieRepository;
        this.movieScreeningRepository = movieScreeningRepository;
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<?> getMovieItems() {
        return ResponseEntity.ok(movieRepository.findAll().stream().map(movie ->
                new MovieItemResponse(movie.getId(), movie.getName(), movie.getImage(), movie.getReleaseDate().toString())
        ));
    }

    //    @GetMapping("/cast")
//    public ResponseEntity<?> getMovieStaff() {
//        var movie = movieRepository.findAll().get(0);
//        return ResponseEntity.ok(movieRepository.findMovieCast(movie.getId()));
//    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieDetails(@PathVariable int id) {
        var screenings = movieScreeningRepository.findAllByMovieId(id);
        var movie = movieRepository.findById(id);
        var actors = movieRepository.findMovieActors(id);
        var directors = movieRepository.findMovieDirectors(id);
        if (screenings == null || actors == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                Map.of(
                        "movieInfo", movie.get(),
                        "movieScreenings", screenings,
                        "movieActors", actors,
                        "movieDirectors", directors
                )
        );
    }
    @PostMapping
    public ResponseEntity<?> insertMovie(@RequestBody MovieInsertRequest movieToInsert){
        var insertedMovie = movieService.insertMovie(movieToInsert);
        return  ResponseEntity.ok( Map.of(
                "movieName", insertedMovie.getName(),
                "movieRelease", insertedMovie.getReleaseDate(),
                "movieImage", insertedMovie.getImage(),
                "movieDesc", insertedMovie.getDescription()
        ));
    }
}
