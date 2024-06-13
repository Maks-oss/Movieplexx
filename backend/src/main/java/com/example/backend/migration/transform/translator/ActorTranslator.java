package com.example.backend.migration.transform.translator;

import com.example.backend.data.nosql.ActorDocument;
import com.example.backend.data.nosql.DirectorDocument;
import com.example.backend.data.nosql.MovieDocument;
import com.example.backend.data.sql.Actor;
import com.example.backend.data.sql.Movie;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component("actor_translator")
public class ActorTranslator implements ItemTranslator<Actor, ActorDocument> {
    @Override
    @Transactional
    public List<ActorDocument> transformData(List<Actor> input) {
        return input.stream().map(actor -> {
            var actorDocument = createActorDocument(actor);
            actorDocument.setMovies(actor.getMovies().stream().map(movie -> {
                var movieDocument = new MovieDocument();
                BeanUtils.copyProperties(movie, movieDocument);
                return movieDocument;
            }).collect(Collectors.toList()));
            return actorDocument;
        }).collect(Collectors.toList());
    }

    private ActorDocument createActorDocument(Actor actor) {
        var actorDocument = new ActorDocument();
        BeanUtils.copyProperties(actor, actorDocument);
        return actorDocument;
    }
}
