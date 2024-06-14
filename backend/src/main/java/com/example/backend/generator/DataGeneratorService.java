package com.example.backend.generator;

import com.example.backend.data.sql.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class DataGeneratorService {
    private final Faker faker = new Faker();
    private Logger generatorLogger = Logger.getLogger("generator");
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void generateData() {
        if (!isTableEmpty()) clearData();
        var managers = generateFirstEmployeesWithRoles();
        var actors = generateActors(5);
        var directors = generateDirectors(2);
        for (int i = 0; i < 10; i++) {
            var movie = generateMovie(i, actors, directors, managers.get(i % 2));
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
                    var seats = generateSeats(movieHall);
                    /* for demonstration purposes. generate 2 screenings for movie halls,
                     *  so that we can assign same seat on same movie hall but with different screening time
                     *  */
                    for (int n = 0; n < 2; n++) {
                        var movieScreening = generateMovieScreening(movie, movieHall);
                        /*
                         * for each customer, generate random num of tickets for that customer and that movie screening.
                         * */
                        for (int j = 0; j < faker.number().numberBetween(1, 6); j++) {
                            generateTicket(customer, movieScreening, seats.get(j));
                        }
                    }
                }
            }
        }
    }

    public List<?> retrieveGeneratedData(String type) {
        DataType dataType = DataType.fromString(type);
        switch (dataType) {
            case MOVIE -> {
                return entityManager
                        .createQuery("""
                                SELECT m FROM Movie m
                                """, Movie.class).getResultList();
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
                        .createQuery("""
                                SELECT t FROM Ticket t
                                """, Map.class).getResultList();
            }
            case SEAT -> {
                return entityManager
                        .createQuery("""
                                SELECT s FROM Seat s
                                """, Map.class).getResultList();
            }
            case CINEMA -> {
                return entityManager
                        .createQuery("""
                                SELECT c FROM Cinema c
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

    private Movie generateMovie(int i, Set<Actor> actors, Set<Director> directors, Employee manager) {
        var movie = new Movie();
        var oscarMovie = faker.oscarMovie();
        movie.setName(oscarMovie.movieName());
        movie.setImage(faker.internet().image(400, 400, String.valueOf(new Random(i).nextInt())));
        movie.setDescription(oscarMovie.quote());

        movie.setReleaseDate(faker.date().birthdayLocalDate());
        movie.setRuntime(faker.number().numberBetween(90, 180));
        movie.setAgeRating(faker.number().numberBetween(6, 18));
        for (Actor el: actors) {
            el.addMovie(movie);
        }
        movie.setActors(new HashSet<>(actors));
        for (Director el : directors) {
            el.addMovie(movie);
        }
        movie.setDirectors(directors);

        movie.setManager(manager);

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

    @Transactional
    public MovieScreening generateMovieScreening(Movie movie, MovieHall movieHall) {

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
            actor.setFirstname(faker.eldenRing().npc());
            actor.setLastname(faker.name().lastName());
            entityManager.persist(actor);
            actors.add(actor);
        }
        return actors;
    }

    private Set<Director> generateDirectors(int numberOfDir) {
        Set<Director> directors = new HashSet<>();
        for (int i = 0; i < numberOfDir; i++) {
            var director = new Director();
            director.setFirstname(faker.leagueOfLegends().champion());
            director.setLastname(faker.name().lastName());
            entityManager.persist(director);
            directors.add(director);
        }
        return directors;
    }

    private void generateMoviePromo(Movie movie, int i) {
        var moviePromo = new MoviePromo();
        moviePromo.setMovie(movie);
        moviePromo.setMoviePromoId(i);
        moviePromo.setTitle(faker.videoGame().title());
        moviePromo.setDescription(faker.movie().quote());
        moviePromo.setImage(faker.internet().image(400, 400, String.valueOf(i)));
        entityManager.persist(moviePromo);
    }

    private Customer generateCustomer() {
        var customerDocument = new Customer();
        customerDocument.setFirstname(faker.name().firstName());
        customerDocument.setLastname(faker.name().lastName());
        customerDocument.setEmail(faker.internet().emailAddress());
        customerDocument.setPassword(faker.internet().password());
        entityManager.persist(customerDocument);
        return customerDocument;
    }

    private void generateTicket(Customer customerDocument, MovieScreening screening, Seat seat) {
        var ticket = new Ticket();
        ticket.setCustomer(customerDocument);
        ticket.setScreening(screening);
        ticket.setPrice(ThreadLocalRandom.current().nextFloat(2.99f, 50.99f));
        ticket.setDateOfIssue(LocalDate.now());
        ticket.setSeat(seat);
        entityManager.persist(ticket);
    }

    private List<Employee> generateFirstEmployeesWithRoles() {
        Role cashierRole = new Role("Cashier", "Sell tickets");
        Role managerRole = new Role("Manager", "Supervises cashier and updates movies");
        List<Employee> managers = new ArrayList<>();
        for (int i = 0; i < 2 ; i++) {
            Employee manager = new Employee();
            manager.setFirstname(faker.elderScrolls().firstName());
            manager.setLastname(faker.elderScrolls().lastName());
            manager.setEmail(faker.internet().emailAddress());
            manager.setPassword(faker.internet().password());
            manager.setRoles(
                    Set.of(managerRole, cashierRole)
            );

            entityManager.persist(manager);
            managerRole.setEmployees(Set.of(manager));
            entityManager.persist(managerRole);
            managers.add(manager);

        }
        for (int i = 0; i < 3; i++) {
            Employee cashier = new Employee();
            cashier.setFirstname(faker.naruto().character());
            cashier.setLastname(faker.elderScrolls().lastName());
            cashier.setEmail(faker.internet().emailAddress());
            cashier.setPassword(faker.internet().password());
            cashier.setRoles(
                    Set.of(cashierRole)
            );
            if (i % 2 == 0) {
                cashier.setManager(managers.get(0));
                entityManager.persist(cashier);
                cashierRole.setEmployees(Set.of(managers.get(0), cashier));
                entityManager.persist(cashierRole);
            } else {
                cashier.setManager(managers.get(1));
                entityManager.persist(cashier);
                cashierRole.setEmployees(Set.of(managers.get(1), cashier));
                entityManager.persist(cashierRole);
            }

        }

        return managers;
    }

    private List<Seat> generateSeats(MovieHall movieHall) {
        int rowsNum = faker.number().numberBetween(10, 15);
        int seatsPerRow = faker.number().numberBetween(8, 12);
        var seats = new ArrayList<Seat>();
        for (int i = 1; i < rowsNum * seatsPerRow; i++) {
            var seat = new Seat();
            seat.setNumber(i);
            seat.setRow(String.valueOf((char) ((i / seatsPerRow) + 65)));
            String seatType = Math.random() < 0.3 ? "vip" : "ordinary";
            seat.setType(seatType);
            seat.setPrice(seatType.equals("vip") ? 20f : 10f);
            seat.setMovieHall(movieHall);
            seats.add(seat);
            entityManager.persist(seat);
        }
        return seats;
    }

}
