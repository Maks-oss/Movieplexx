package com.example.backend.migration.transform.translator;

import com.example.backend.data.nosql.CinemaDocument;
import com.example.backend.data.sql.Cinema;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("cinema_translator")
public class CinemaTranslator implements ItemTranslator<Cinema, CinemaDocument>{

    @Override
    public List<CinemaDocument> transformData(List<Cinema> input) {
        return input.stream().map(cinema -> {
            var cinemaDocument = new CinemaDocument();
            BeanUtils.copyProperties(cinema, cinemaDocument);
            return cinemaDocument;
        }).collect(Collectors.toList());
    }
}
