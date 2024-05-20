package com.example.backend.generator;

import com.example.backend.data.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DataGeneratorService {
    private final Faker faker = new Faker();
    private Logger generatorLogger = Logger.getLogger("generator");
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void generateData(boolean generateNew) {
        if (generateNew || isTableEmpty()) {
            if (!isTableEmpty()) clearData();
            generateFirstEmployeesWithRoles();
            for (int i = 0; i < 10; i++) {
                var customer = generateCustomer();
//                var employee = generateEmployee(i % 2 == 0 ? "Manager" : "Cashier",roles);
                var movie = generateMovie();
                generateActors(movie);
                generateDirectors(movie);
                generateMoviePromo(movie);
                var cinema = generateCinema();
                for (int j = 0; j < 5; j++) {
                    var movieHall = generateMovieHall(cinema);
                    var movieScreening = generateMovieScreening(movie, movieHall);
                    generateSeats(movieHall);
                }
            }
        }
    }

    public List<?> retrieveGeneratedData(String type) {
        DataType dataType = DataType.fromString(type);
        switch (dataType){
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
            case EMPLOYEE ->{
                return entityManager
                        .createNativeQuery("""
                        SELECT e.firstname, e.lastname, r.name AS rolename, e.managerid AS supervisorid, r.description AS roledescription FROM Employee e
                        INNER JOIN EmployeeRole er on er.employeeid = e.employeeid
                        INNER JOIN Role r on r.roleid = er.roleid
                        """, Map.class).getResultList();
            }
            case CUSTOMER -> {
                return entityManager
                        .createNativeQuery("""
                        SELECT * FROM Customer
                        """, Map.class).getResultList();
            }
        }
        return null;
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
        return true;
    }

    private void clearData() {
        entityManager.createNativeQuery("""
                            TRUNCATE TABLE 
                            Role, EmployeeRole, Seat, Employee, 
                            Customer, Actor, Director, MoviePromo, 
                            MovieScreening, Movie, MovieHall, Cinema 
                            CASCADE
                """).executeUpdate();
        generatorLogger.log(Level.INFO, "Deletion " + isTableEmpty());
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
        // Generate random values for seconds and nanoseconds
        long randomSeconds = ThreadLocalRandom.current().nextLong(System.currentTimeMillis() / 1000);
        int randomNanos = ThreadLocalRandom.current().nextInt(0, 999999999);
        Instant randomInstant = Instant.ofEpochSecond(randomSeconds, randomNanos);

        movieScreening.setStartTime(randomInstant);
        movieScreening.setEndTime(randomInstant.plus(movie.getRuntime(), ChronoUnit.MINUTES));
        movieScreening.setMovie(movie);
        movieScreening.setMoviehall(movieHall);
        entityManager.persist(movieScreening);
        return movieScreening;
    }

    private void generateActors(Movie movie) {
        for (int i = 0; i < 5; i++) {
            var actor = new Actor();
            actor.setFirstname(faker.elderScrolls().firstName());
            actor.setLastname(faker.elderScrolls().lastName());
            actor.setMovie(movie);
            entityManager.persist(actor);
        }
    }

    private void generateDirectors(Movie movie) {
        for (int i = 0; i < 2; i++) {
            var director = new Director();
            director.setFirstname(faker.elderScrolls().firstName());
            director.setLastname(faker.elderScrolls().lastName());
            director.setMovie(movie);
            entityManager.persist(director);
        }
    }

    private void generateMoviePromo(Movie movie) {
        var moviePromo = new MoviePromo();
        moviePromo.setMovie(movie);
        moviePromo.setTitle(faker.videoGame().title());
        moviePromo.setDescription(faker.movie().quote());
        moviePromo.setImage(null);
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

    /*@SuppressWarnings("unchecked")
    private Employee generateEmployee(String position, Set<Role> roles) {
        var employee = new Employee();
        employee.setFirstname(faker.naruto().character());
        employee.setLastname(faker.naruto().demon());
        employee.setEmail(faker.internet().emailAddress());
        employee.setPassword(faker.internet().password());

        if (position.equals("manager")) {
            generatorLogger.log(Level.INFO, "Manager position");
            employee.setManager(null);
            employee.setRoles(roles);
        } else {
            // set manager for cashier
            List<Employee> managers = entityManager.createNativeQuery("""
                    SELECT em.employeeid, em.managerid,em.firstname, em.lastname, em.email, em.password FROM Role r
                    INNER JOIN EmployeeRole emr on emr.roleid = r.roleid
                    INNER JOIN Employee em on em.employeeid = emr.employeeid
                    WHERE r.name = 'Manager'
                    """, Employee.class).getResultList();
            employee.setManager(managers.get(faker.number().numberBetween(0, managers.size() - 1)));
            employee.setRoles(roles
                    .stream()
                    .filter(r -> r.getName().equals("Cashier"))
                    .collect(Collectors.toSet())
            );
        }
        entityManager.persist(employee);
        return employee;
    }*/

    /*private Set<Role> generateRoles() {
        Role cashierRole = new Role("Cashier", "Sell tickets");
        Role managerRole = new Role("Manager", "Supervises cashier and updates movies");
        cashierRole.setEmployees(
                Set.of(new Employee(), new Employee())
        );
        managerRole.setEmployees(
                Set.of(new Employee())
        );
        entityManager.persist(cashierRole);
        entityManager.persist(managerRole);
        return Set.of(cashierRole, managerRole);
    }*/
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
//        return Set.of(cashierRole, managerRole);
    }

    private void generateSeats(MovieHall movieHall) {
        for (int i = 1; i <= 50; i++) {
            var seat = new Seat();
            seat.setNumber(i);
            seat.setRow(faker.letterify("?"));
            seat.setType(faker.restaurant().type());
            seat.setMovieHall(movieHall);
            entityManager.persist(seat);
        }
    }
}
