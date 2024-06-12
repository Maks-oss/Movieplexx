package com.example.backend.implementation.movie;

import com.example.backend.data.sql.*;
import com.example.backend.generator.DataGeneratorService;
import com.example.backend.implementation.actor.ActorRepository;
import com.example.backend.implementation.director.DirectorRepository;
import com.example.backend.implementation.employee.EmployeeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final MovieHallRepository movieHallRepository;
    private final DataGeneratorService generatorService;
    private final MovieScreeningRepository movieScreeningRepository;
    private final EmployeeRepository employeeRepository;


    public MovieService(MovieRepository movieRepository, ActorRepository actorRepository, DirectorRepository directorRepository, MovieHallRepository movieHallRepository, DataGeneratorService generatorService, MovieScreeningRepository movieScreeningRepository, EmployeeRepository employeeRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.directorRepository = directorRepository;
        this.movieHallRepository = movieHallRepository;
        this.generatorService = generatorService;
        this.movieScreeningRepository = movieScreeningRepository;
        this.employeeRepository = employeeRepository;
    }

    public Movie insertMovie(MovieInsertRequest movieInsertRequest) {
        Movie movie = new Movie();
        movie.setName(movieInsertRequest.name());
        movie.setDescription(movieInsertRequest.description());
        movie.setImage(movieInsertRequest.image());
        movie.setRuntime(movieInsertRequest.runTime());
        movie.setReleaseDate(movieInsertRequest.releaseDate());
        movie.setAgeRating(movieInsertRequest.ageRating());
        Employee manager = employeeRepository.findById(movieInsertRequest.managerId()).get();
        movie.setManager(manager);

        Set<Actor> actors = new HashSet<>();
        for (Integer actorId : movieInsertRequest.actorIds()) {
            Optional<Actor> actor = actorRepository.findById(actorId);
            if (actor.isPresent()){
                Actor a = actor.get();
                a.addMovie(movie);
                actors.add(a);
            }
        }
        movie.setActors(actors);

        Set<Director> directors = new HashSet<>();
        for (Integer directorId : movieInsertRequest.directorIds()) {
            Optional<Director> director = directorRepository.findById(directorId);
            if (director.isPresent()){
                Director d = director.get();
                d.addMovie(movie);
                directors.add(d);
            }
        }
        movie.setDirectors(directors);

        movieRepository.save(movie);

        var movieHalls = movieHallRepository.findAll();
        for (int i = 0; i < movieHalls.size() / 3; i++) {
            var movieScreening = generatorService.generateMovieScreening(movie, movieHalls.get(i));
        }
        Set<Movie> movies = manager.getManagedMovies();
        movies.add(movie);
        manager.setManagedMovies(movies);

        return movie;
    }

    public List<MovieItemResponse> getMovieItemsList() {
        return movieRepository.findAll().stream().map(movie ->
                new MovieItemResponse(movie.getId(), movie.getName(), movie.getImage(), movie.getReleaseDate().toString())
        ).collect(Collectors.toList());
    }

    public Map<String, Object> getMovieCast(int movieId) {
        return Map.of(
                "actors", movieRepository.findMovieActors(movieId),
                "directors", movieRepository.findMovieDirectors(movieId)
        );
    }

    public List<MovieScreening> getMovieScreenings(int movieId) {
        return movieScreeningRepository.findAllByMovieId(movieId, Sort.by(Sort.Direction.ASC, "moviehall"));
    }

}
