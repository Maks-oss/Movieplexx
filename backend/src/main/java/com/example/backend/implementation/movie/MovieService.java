package com.example.backend.implementation.movie;

import com.example.backend.data.Actor;
import com.example.backend.data.Director;
import com.example.backend.data.Movie;
import com.example.backend.implementation.actor.ActorRepository;
import com.example.backend.implementation.director.DirectorRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieService {
    private MovieRepository movieRepository;
    private ActorRepository actorRepository;
    private DirectorRepository directorRepository;

    public MovieService(MovieRepository movieRepository, ActorRepository actorRepository, DirectorRepository directorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.directorRepository = directorRepository;
    }

    public Movie insertMovie (MovieInsertRequest movieInsertRequest){
        Movie movie = new Movie();
        movie.setName(movieInsertRequest.name());
        movie.setDescription(movieInsertRequest.description());
        movie.setImage(movieInsertRequest.image());
        movie.setRuntime(movieInsertRequest.runTime());
        movie.setReleaseDate(movieInsertRequest.releaseDate());
        movie.setAgeRating(movieInsertRequest.ageRating());

        if (movie.getActors() == null) {
            movie.setActors(new HashSet<>());
        }
        if (movie.getDirectors() == null) {
            movie.setDirectors(new HashSet<>());
        }

        for (Integer actorId : movieInsertRequest.actorIds()) {
            Optional<Actor> actor = actorRepository.findById(actorId);
            if(actor.isPresent())
                movie.addActor(actor.get());
        }

        for (Integer directorId : movieInsertRequest.directorIds()) {
            Optional<Director> director = directorRepository.findById(directorId);
            if(director.isPresent())
                movie.addDirector(director.get());
        }

        return movieRepository.save(movie);
    }
}
