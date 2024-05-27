package com.example.backend.implementation.movie;

import com.example.backend.data.*;
import com.example.backend.generator.DataGeneratorService;
import com.example.backend.implementation.actor.ActorRepository;
import com.example.backend.implementation.director.DirectorRepository;
import com.example.backend.implementation.movieHall.MovieHallRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieService {
    private MovieRepository movieRepository;
    private ActorRepository actorRepository;
    private DirectorRepository directorRepository;
    private MovieHallRepository movieHallRepository;
    private DataGeneratorService generatorService;

    public MovieService(MovieRepository movieRepository, ActorRepository actorRepository, DirectorRepository directorRepository, MovieHallRepository movieHallRepository, DataGeneratorService generatorService) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.directorRepository = directorRepository;
        this.movieHallRepository = movieHallRepository;
        this.generatorService = generatorService;
    }

    public Movie insertMovie(MovieInsertRequest movieInsertRequest) {
        Movie movie = new Movie();
        movie.setName(movieInsertRequest.name());
        movie.setDescription(movieInsertRequest.description());
        movie.setImage(movieInsertRequest.image());
        movie.setRuntime(movieInsertRequest.runTime());
        movie.setReleaseDate(movieInsertRequest.releaseDate());
        movie.setAgeRating(movieInsertRequest.ageRating());

        Set<Actor> actors = new HashSet<>();
        for (Integer actorId : movieInsertRequest.actorIds()) {
            Optional<Actor> actor = actorRepository.findById(actorId);
            if (actor.isPresent()){
                actor.get().addMovie(movie);
                actors.add(actor.get());
            }
        }
        movie.setActors(actors);

        Set<Director> directors = new HashSet<>();
        for (Integer directorId : movieInsertRequest.directorIds()) {
            Optional<Director> director = directorRepository.findById(directorId);
            if (director.isPresent()){
                director.get().addMovie(movie);
                directors.add(director.get());
            }
        }
        movie.setDirectors(directors);

        movieRepository.save(movie);

        var movieHalls = movieHallRepository.findAll();
        for (int i = 0; i < movieHalls.size() / 3; i++) {
            var movieScreening = generatorService.generateMovieScreening(movie, movieHalls.get(i));
        }

        return movie;
    }

}
