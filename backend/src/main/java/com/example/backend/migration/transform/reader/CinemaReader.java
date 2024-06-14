package com.example.backend.migration.transform.reader;

import com.example.backend.data.sql.Cinema;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("cinema_reader")
public class CinemaReader extends SqlItemReader<Cinema>{
    @Override
    public List<Cinema> readItems() {
        return entityManager
                .createQuery("SELECT c FROM Cinema c", Cinema.class)
                .getResultList();
    }
}
