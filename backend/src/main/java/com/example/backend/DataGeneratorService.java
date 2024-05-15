package com.example.backend;

import com.example.backend.data.Cinema;
import com.example.backend.data.Movie;
import com.example.backend.data.MovieHall;
import com.example.backend.data.MovieScreening;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import net.datafaker.Faker;
import org.springframework.jmx.export.assembler.AbstractConfigurableMBeanInfoAssembler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DataGeneratorService {
    private Faker faker = new Faker();
    private Logger generatorLogger = Logger.getLogger("generator");
    @PersistenceContext // or even @Autowired
    private EntityManager entityManager;

    @Transactional
    public void generateData(boolean generateNew) {
        if (generateNew || isTableEmpty()) {
            if (!isTableEmpty()) clearData();
            for (int i = 0; i < 10; i++) {
                var movie = generateMovie();
                var cinema = generateCinema(i);
                var movieHall = generateMovieHall(cinema);
                var movieScreening = generateMovieScreening(movie, movieHall);
            }
        }
    }
    public List<?> retrieveGeneratedData() {
        return (List<?>) entityManager
                .createQuery("""
                        SELECT m from MovieScreening m
                        """)
                .getResultList();
    }
    private boolean isTableEmpty() {
        Query query = entityManager.createNativeQuery("SELECT COUNT(m) FROM Movie m");
        List<?> result = query.getResultList();
        if (!result.isEmpty()) {
            Object count = result.get(0);
            if (count instanceof Number) {
                return ((Number) count).intValue() == 0;
            }
        }
        // If the result is empty or not a number, assume the table is empty
        return true;
    }
    private void clearData() {
        entityManager.createQuery("DELETE FROM Movie").executeUpdate();
        entityManager.createQuery("DELETE FROM MovieHall ").executeUpdate();
        entityManager.createQuery("DELETE FROM Cinema ").executeUpdate();
    }
    private Movie generateMovie() {
        var movie = new Movie();
        var oscarMovie = faker.oscarMovie();
        movie.setName(oscarMovie.movieName());
        movie.setDescription(oscarMovie.quote());
        movie.setReleaseDate(faker.date().birthdayLocalDate());
        movie.setRuntime(faker.number().numberBetween(90, 180));
        movie.setAgeRating(faker.number().numberBetween(6, 18));
        entityManager.persist(movie);
        return movie;
    }
    private Cinema generateCinema(int i) {
        var cinema = new Cinema();
        cinema.setName(faker.leagueOfLegends().location());
        cinema.setCity(faker.address().city());
        cinema.setCountry(faker.address().country());
        cinema.setAddress(faker.address().fullAddress());
        entityManager.persist(cinema);
        return cinema;
    }
    private MovieHall generateMovieHall(Cinema cinema) {
        var movieHall = new MovieHall();
        movieHall.setNumber((int) faker.number().randomNumber());
        movieHall.setType(faker.pokemon().type());
        movieHall.setCinema(cinema);
        entityManager.persist(movieHall);
        return movieHall;
    }

    private MovieScreening generateMovieScreening(Movie movie, MovieHall movieHall) {
        var movieScreening = new MovieScreening();
        movieScreening.setStartTime(Instant.now());
        movieScreening.setEndTime(Instant.now().plus(movie.getRuntime(), ChronoUnit.MINUTES));
        movieScreening.setMovie(movie);
        movieScreening.setMoviehall(movieHall);
        entityManager.persist(movieScreening);
        return movieScreening;
    }

}
