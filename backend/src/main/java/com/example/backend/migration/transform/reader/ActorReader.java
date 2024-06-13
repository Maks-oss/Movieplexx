package com.example.backend.migration.transform.reader;

import com.example.backend.data.nosql.ActorDocument;
import com.example.backend.data.sql.Actor;
import com.example.backend.data.sql.Movie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component("actor_reader")
public class ActorReader extends SqlItemReader<Actor> {
    @Override
    public List<Actor> readItems() {
        return entityManager
                .createQuery("""
                             SELECT a FROM Actor a
                             JOIN FETCH a.movies
                             """, Actor.class)
                .getResultList();
    }
}
