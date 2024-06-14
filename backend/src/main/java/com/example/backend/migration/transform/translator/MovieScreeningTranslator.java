package com.example.backend.migration.transform.translator;

import com.example.backend.data.nosql.CinemaDocument;
import com.example.backend.data.nosql.MovieHallDocument;
import com.example.backend.data.nosql.MovieScreeningDocument;
import com.example.backend.data.sql.Cinema;
import com.example.backend.data.sql.MovieHall;
import com.example.backend.data.sql.MovieScreening;
import org.apache.juli.logging.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component("movie_screening_translator")
public class MovieScreeningTranslator implements ItemTranslator<Map, MovieScreeningDocument>{
    @Override
    public List<MovieScreeningDocument> transformData(List<Map> input) {
        return input.stream().map(movieScreening -> {
            var movieScreeningDocument = new MovieScreeningDocument();
            movieScreeningDocument.setId((Integer) movieScreening.get("id"));
            movieScreeningDocument.setStartTime((Instant) movieScreening.get("startTime"));
            movieScreeningDocument.setEndTime((Instant) movieScreening.get("endTime"));
            movieScreeningDocument.setMoviehall(movieHallDocument((MovieHall) movieScreening.get("movieHall")));
            movieScreeningDocument.setMovie((Integer) movieScreening.get("movieId"));
            return movieScreeningDocument;
        }).collect(Collectors.toList());
    }

    private MovieHallDocument movieHallDocument(MovieHall movieHall) {
        var movieHallDocument = new MovieHallDocument();
        BeanUtils.copyProperties(movieHall, movieHallDocument);
        var cinema = new CinemaDocument();
        BeanUtils.copyProperties(movieHall.getCinema(), cinema);
        movieHallDocument.setCinema(cinema);
        return movieHallDocument;
    }
}
