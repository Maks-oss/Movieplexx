package com.example.backend.migration.transform.reader;

import com.example.backend.data.sql.Actor;
import com.example.backend.data.sql.Director;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("director_reader")
public class DirectorReader extends SqlItemReader<Director> {
    @Override
    public List<Director> readItems() {
        return entityManager
                .createQuery("""
                             SELECT d FROM Director d
                             JOIN FETCH d.movies
                             """, Director.class)
                .getResultList();
    }
}
