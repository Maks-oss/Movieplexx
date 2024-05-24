package com.example.backend.generator;

import com.example.backend.data.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DataGeneratorService {
    private final Faker faker = new Faker();
    private Logger generatorLogger = Logger.getLogger("generator");

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void generateData() {
        if (!isTableEmpty()) clearData();
        generateFirstEmployeesWithRoles();
        var actors = generateActors(5);
        var directors = generateDirectors(2);
        for (int i = 0; i < 10; i++) {
            var movie = generateMovie(i,actors,directors);
            generateMoviePromo(movie, i);
            /*
             * For each movie, generate random num of cinemas
             * */
            for (int s = 0; s < faker.number().numberBetween(2, 4); s++) {
                var cinema = generateCinema();
                /*
                 * For each cinema, generate random num of customers, movie-halls, seats and movie screenings
                 * */
                for (int k = 0; k < faker.number().numberBetween(3, 10); k++) {
                    var customer = generateCustomer();
                    var movieHall = generateMovieHall(cinema);
                    /* generate random num of seats for movie-hall*/
                    generateSeats(movieHall);
                    var movieScreening = generateMovieScreening(movie, movieHall);
                    /*
                     * for each customer, generate random num of tickets for that customer and that movie screening
                     * */
                    for (int j = 0; j < faker.number().numberBetween(1, 6); j++) {
                        generateTicket(customer, movieScreening);
                    }
                }
            }
        }

    }

    public List<?> retrieveGeneratedData(String type) {
        DataType dataType = DataType.fromString(type);
        switch (dataType) {
            case MOVIE_DATA -> {
                return entityManager
                        .createNativeQuery("""
                                SELECT m.name AS moviename, mp.title AS movietitle, mh.number AS hallnumber, mv.starttime AS screeningtime,  CONCAT(d.firstname, ' ', d.lastname) AS director, c.name AS branchname, c.address AS branchlocation FROM Movie m
                                INNER JOIN MovieScreening mv on mv.movieid = m.movieid
                                INNER JOIN MovieHall mh on mh.hallid = mv.moviehallid
                                INNER JOIN MoviePromo mp on mp.movieid = m.movieid
                                INNER JOIN Director d on d.movieid = m.movieid
                                INNER JOIN Cinema c on c.cinemaid = mh.cinemaid
                                """, Map.class).getResultList();
            }
            case EMPLOYEE -> {
                return entityManager
                        .createNativeQuery("""
                                SELECT e.firstname, e.lastname, r.name AS rolename, e.managerid AS supervisorid, r.description AS roledescription FROM Employee e
                                INNER JOIN EmployeeRole er on er.employeeid = e.employeeid
                                INNER JOIN Role r on r.roleid = er.roleid
                                """, Map.class).getResultList();
            }
            case CUSTOMER -> {
                return entityManager
                        .createQuery("""
                                SELECT c, t.price FROM Customer c
                                INNER JOIN Ticket t on t.customer.id = c.id
                             
                                """, Map.class).getResultList();
            }
            case TICKET -> {
                return entityManager
                        .createNativeQuery("""
                                SELECT * FROM Ticket
                                """, Map.class).getResultList();
            }
            case SEAT -> {
                return entityManager
                        .createNativeQuery("""
                                SELECT * FROM Seat
                                """, Map.class).getResultList();
            }
            case CINEMA -> {
                return entityManager
                        .createNativeQuery("""
                                SELECT * FROM Cinema
                                """, Map.class).getResultList();
            }
        }
        return null;
    }

    private boolean isTableEmpty() {
        Query query = entityManager.createNativeQuery("SELECT COUNT(m) FROM Ticket m");
        List<?> result = query.getResultList();
        if (!result.isEmpty()) {
            Object count = result.get(0);
            if (count instanceof Number) {
                return ((Number) count).intValue() == 0;
            }
        }
        return true;
    }

    private void clearData() {
        entityManager.createNativeQuery("""
                            TRUNCATE TABLE 
                            Role, EmployeeRole, Seat, Employee, 
                            Customer, Actor, Director, MoviePromo, 
                            MovieScreening, Movie, MovieHall, Cinema, Ticket
                            CASCADE
                """).executeUpdate();
        generatorLogger.log(Level.INFO, "Deletion " + isTableEmpty());
    }

    private Movie generateMovie(int i, Set<Actor> actors, Set<Director> directors) {
        var movie = new Movie();
        var oscarMovie = faker.oscarMovie();
        movie.setName(oscarMovie.movieName());
        movie.setImage("https://source.unsplash.com/random/400x400?sig=" + i);
        movie.setDescription(oscarMovie.quote());
        movie.setReleaseDate(faker.date().birthdayLocalDate());
        movie.setRuntime(faker.number().numberBetween(90, 180));
        movie.setAgeRating(faker.number().numberBetween(6, 18));

        if (movie.getActors() == null) {
            movie.setActors(new HashSet<>());
        }
        if (movie.getDirectors() == null) {
            movie.setDirectors(new HashSet<>());
        }

        List<Actor> actorList = new ArrayList<>(actors);
        Collections.shuffle(actorList);
        for (int j = 0; j < actorList.size(); j++) {
            Actor actor = actorList.get(j);
            movie.addActor(actor);
        }

        List<Director> directorList = new ArrayList<>(directors);
        Collections.shuffle(directorList);
        for (int j = 0; j < directorList.size(); j++) {
            Director director = directorList.get(j);
            movie.addDirector(director);
        }

        entityManager.persist(movie);
        return movie;
    }

    private Cinema generateCinema() {
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
        Timestamp start = Timestamp.valueOf(faker.date().future(30, TimeUnit.DAYS).toLocalDateTime().withMinute(0).withSecond(0).withNano(0));
        Timestamp end = new Timestamp(start.getTime() + TimeUnit.MINUTES.toMillis(movie.getRuntime()));
        movieScreening.setStartTime(start.toInstant());
        movieScreening.setEndTime(end.toInstant());
        movieScreening.setMovie(movie);
        movieScreening.setMoviehall(movieHall);
        entityManager.persist(movieScreening);
        return movieScreening;
    }

    private Set<Actor> generateActors(int numberOfAct) {
        Set<Actor> actors = new HashSet<>();
        for (int i = 0; i < numberOfAct; i++) {
            var actor = new Actor();
            actor.setFirstname(faker.elderScrolls().firstName());
            actor.setLastname(faker.elderScrolls().lastName());
            entityManager.persist(actor);
            actors.add(actor);
        }
        return actors;
    }

    private Set<Director> generateDirectors(int numberOfDir) {
        Set<Director> directors = new HashSet<>();
        for (int i = 0; i < numberOfDir; i++) {
            var director = new Director();
            director.setFirstname(faker.elderScrolls().firstName());
            director.setLastname(faker.elderScrolls().lastName());
            entityManager.persist(director);
            directors.add(director);
        }
        return directors;
    }

    private void generateMoviePromo(Movie movie, int i) {
        var moviePromo = new MoviePromo();
        moviePromo.setMovie(movie);
        moviePromo.setTitle(faker.videoGame().title());
        moviePromo.setDescription(faker.movie().quote());
        moviePromo.setImage("https://source.unsplash.com/random/400x400?sig=" + i);
        entityManager.persist(moviePromo);
    }

    private Customer generateCustomer() {
        var customer = new Customer();
        customer.setFirstname(faker.naruto().character());
        customer.setLastname(faker.naruto().demon());
        customer.setEmail(faker.internet().emailAddress());
        customer.setPassword(faker.internet().password());
        entityManager.persist(customer);
        return customer;
    }

    private void generateTicket(Customer customer, MovieScreening screening) {
        var ticket = new Ticket();
        ticket.setCustomer(customer);
        ticket.setScreening(screening);
        ticket.setPrice(ThreadLocalRandom.current().nextFloat(2.99f, 50.99f));
        ticket.setDateOfIssue(LocalDate.now());
        entityManager.persist(ticket);
    }

    private void generateFirstEmployeesWithRoles() {
        Role cashierRole = new Role("Cashier", "Sell tickets");
        Role managerRole = new Role("Manager", "Supervises cashier and updates movies");
        Employee manager = new Employee();
        manager.setFirstname(faker.elderScrolls().firstName());
        manager.setLastname(faker.elderScrolls().lastName());
        manager.setEmail(faker.internet().emailAddress());
        manager.setPassword(faker.internet().password());
        manager.setRoles(
                Set.of(managerRole, cashierRole)
        );
        entityManager.persist(manager);
        for (int i = 0; i < 3; i++) {
            Employee cashier = new Employee();
            cashier.setFirstname(faker.naruto().character());
            cashier.setLastname(faker.elderScrolls().lastName());
            cashier.setEmail(faker.internet().emailAddress());
            cashier.setPassword(faker.internet().password());
            cashier.setRoles(
                    Set.of(cashierRole)
            );
            cashier.setManager(manager);
            entityManager.persist(cashier);
            cashierRole.setEmployees(Set.of(manager, cashier));
            entityManager.persist(cashierRole);
        }
        managerRole.setEmployees(Set.of(manager));
        entityManager.persist(managerRole);
    }

    private void generateSeats(MovieHall movieHall) {
        int rowsNum = faker.number().numberBetween(10, 15);
        int seatsPerRow = 10;
        for (int i = 1; i < rowsNum * seatsPerRow; i++) {
            var seat = new Seat();
            seat.setNumber(i);
            seat.setRow(String.valueOf((char) ((i / seatsPerRow) + 65)));
            String seatType = Math.random() < 0.3 ? "vip" : "ordinary";
            seat.setType(seatType);
            seat.setPrice(seatType.equals("vip") ? 20f : 10f);
            seat.setMovieHall(movieHall);
            entityManager.persist(seat);
        }
    }
}
