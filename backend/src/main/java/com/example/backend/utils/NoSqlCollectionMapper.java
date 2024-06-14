package com.example.backend.utils;

import com.example.backend.data.nosql.ActorDocument;
import com.example.backend.data.nosql.MovieDocument;
import com.example.backend.data.sql.Actor;
import com.example.backend.data.sql.Movie;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

public class NoSqlCollectionMapper {
    private NoSqlCollectionMapper() {}

    public static MovieDocument mapToMovieDocument(Movie movie) {
        var movieDocument = new MovieDocument();
        BeanUtils.copyProperties(movie, movieDocument);
        movieDocument.setActors(movie.getActors().stream().map(NoSqlCollectionMapper::mapToActorDocument).collect(Collectors.toList()));
        return movieDocument;
    }

    public static Movie mapToMovie(MovieDocument movieDocument) {
        var movie = new Movie();
        BeanUtils.copyProperties(movieDocument, movie);
        movie.setActors(movieDocument.getActors().stream().map(NoSqlCollectionMapper::mapToActor).collect(Collectors.toSet()));
        return movie;
    }
    public static Actor mapToActor(ActorDocument actorDocument) {
        var actor = new Actor();
        BeanUtils.copyProperties(actorDocument, actor);
        actor.setMovies(actorDocument.getMovies().stream().map(NoSqlCollectionMapper::mapToMovie).collect(Collectors.toSet()));
        return actor;
    }
    public static ActorDocument mapToActorDocument(Actor actor) {
        var actorDocument = new ActorDocument();
        BeanUtils.copyProperties(actor, actorDocument);
        actorDocument.setMovies(actor.getMovies().stream().map(NoSqlCollectionMapper::mapToMovieDocument).collect(Collectors.toList()));
        return actorDocument;
    }
}
