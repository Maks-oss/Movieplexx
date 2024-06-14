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
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component("actor_translator")
public class ActorTranslator implements ItemTranslator<Map, ActorDocument> {
    @Override
    @Transactional
    public List<ActorDocument> transformData(List<Map> input) {
        return input.stream().map(actor -> {
            var actorDocument = createActorDocument(actor);
            List<Movie> movies = (List<Movie>) actor.get("movies");
            actorDocument.setMovies(movies.stream().map(movie -> {
                var movieDocument = new MovieDocument();
                BeanUtils.copyProperties(movie, movieDocument);
                return movieDocument;
            }).collect(Collectors.toList()));
            return actorDocument;
        }).collect(Collectors.toList());
    }

    private ActorDocument createActorDocument(Map actor) {
        var actorDocument = new ActorDocument();
        actorDocument.setId((Integer) actor.get("id"));
        actorDocument.setFirstname((String) actor.get("firstName"));
        actorDocument.setLastname((String) actor.get("lastName"));
//        BeanUtils.copyProperties(actor, actorDocument);
        return actorDocument;
    }

}
