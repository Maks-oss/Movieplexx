package com.example.backend.usecases.movie;

import com.example.backend.data.MoviePromo;
import com.example.backend.data.MovieScreening;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MoviePromoRepository moviePromoRepository;
    @GetMapping
    public ResponseEntity<?> getMoviesPromos() {
//        List<MoviePromo> all = moviePromoRepository.findAll();
//        Logger.getLogger("Movies").log(Level.INFO, "Movies: " + all);
        return ResponseEntity.ok(moviePromoRepository.findAll().stream().map(promo ->
                new MovieDto(promo.getMovie().getName(), null, promo.getDescription())
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieDetails(@RequestParam int movieId){
        var movieScreening = new MovieScreening();
        return ResponseEntity.ok().build();
    }
}
