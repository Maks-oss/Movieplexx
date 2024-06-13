package com.example.backend.migration.transform.translator;

import com.example.backend.data.nosql.MovieHallDocument;
import com.example.backend.data.sql.MovieHall;
import com.example.backend.data.sql.Seat;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("hall_translator")
public class MovieHallTranslator implements ItemTranslator<Map<String, Object>, MovieHallDocument>{
    @Override
    public List<MovieHallDocument> transformData(List<Map<String, Object>> input) {
        return input.stream().map(entry -> {
            var movieHall = (MovieHall)entry.get("hall");
            var seats = (List<Seat>)entry.get("seats");
            var movieHallDocument = new MovieHallDocument();
            BeanUtils.copyProperties(movieHall, movieHallDocument);
            movieHallDocument.setSeatList(seats);
            return movieHallDocument;
        }).collect(Collectors.toList());
    }
}
