package com.example.backend.migration.transform.reader;

import com.example.backend.implementation.movie.sql.MovieHallRepository;
import com.example.backend.implementation.seats.sql.SeatsRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("hall_reader")
public class MovieHallReader extends SqlItemReader<Map<String, Object>> {
    private MovieHallRepository movieHallRepository;
    private SeatsRepository seatsRepository;

    public MovieHallReader(MovieHallRepository movieHallRepository, SeatsRepository seatsRepository) {
        this.movieHallRepository = movieHallRepository;
        this.seatsRepository = seatsRepository;
    }

    @Override
    public List<Map<String, Object>> readItems() {
        return movieHallRepository.findAll().stream().map(movieHall -> {
            var seats = seatsRepository.findSeatsByMovieHallId(movieHall.getId(), Sort.unsorted());
            return Map.of(
                    "hall", movieHall,
                    "seats", seats
            );
        }).collect(Collectors.toList());
    }
}
