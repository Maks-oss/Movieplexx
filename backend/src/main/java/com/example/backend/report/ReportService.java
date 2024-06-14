package com.example.backend.report;


import com.example.backend.data.nosql.CinemaDocument;
import com.example.backend.data.nosql.EmployeeDocument;
import com.example.backend.data.sql.Cinema;
import com.example.backend.data.sql.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ReportService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private MongoTemplate mongoTemplate;
    public Map<String, Object> createReportNoSql() {
        var cinemas = mongoTemplate.findAll(CinemaDocument.class);
        Map<String, Object> map = new HashMap<>();
        for (CinemaDocument cinema : cinemas) {
            map.put(cinema.getName(), getTop5Customers(cinema.getId()));
        }
        return map;
    }

    public List<Map> getTop5Customers(int cinemaId) {
        AggregationOperation lookupTickets = Aggregation.lookup("Ticket", "_id", "customerid", "customer_tickets");

        AggregationOperation lookupScreeningDetails = Aggregation.lookup("MovieScreening",
                "customer_tickets._id.screeningId", "_id", "screening_details");

        AggregationOperation lookupHallDetails = Aggregation.lookup("MovieHall",
                "screening_details.moviehall", "_id", "hall_details"
        );
        AggregationOperation lookupCinemaDetails = Aggregation.lookup("Cinema",
                "hall_details.cinema", "_id", "cinema_details");

        AggregationOperation matchCinema = Aggregation.match(
                Criteria.where("cinema_details._id").is(cinemaId)
        );
        AggregationOperation unwindTickets = Aggregation.unwind("customer_tickets");

        AggregationOperation groupByCustomer = Aggregation.group("_id", "firstname", "lastname")
                .sum("customer_tickets.price").as("totalPrice");

        AggregationOperation projectCustomer = Aggregation.project()
                .and("$_id.firstname").as("firstName")
                .and("$_id.lastname").as("lastName")
                .and("totalPrice").as("totalPrice")
                .andExclude("_id");

        AggregationOperation sortByTotalPrice = Aggregation.sort(Sort.by(Sort.Direction.DESC, "totalPrice"));

        AggregationOperation limitResults = Aggregation.limit(5);

        Aggregation aggregation = Aggregation.newAggregation(
                lookupTickets,
                lookupScreeningDetails,
                lookupHallDetails,
                lookupCinemaDetails,
                matchCinema,
                unwindTickets,
                groupByCustomer,
                projectCustomer,
                sortByTotalPrice,
                limitResults
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "Customer", Map.class);
        return results.getMappedResults().stream().map(map -> Map.of(
                "customerName", map.get("firstName") + " " + map.get("lastName"),
                "totalPrice", map.get("totalPrice")
        )).collect(Collectors.toList());
    }


    @Transactional
    public Map<String, Object> createReport() {
        var cinemas = entityManager.createQuery("SELECT c FROM Cinema c", Cinema.class).getResultList();
        Map<String, Object> map = new HashMap<>();
        for (Cinema cinema : cinemas) {
            map.put(cinema.getName(), entityManager
                    .createQuery("""
                                SELECT concat(cus.firstname, ' ', cus.lastname) AS customerName, SUM(t.price) AS totalPrice FROM Customer cus
                                INNER JOIN Ticket t on t.customer.id = cus.id
                                INNER JOIN MovieScreening mv on mv.id = t.screening.id
                                INNER JOIN MovieHall mh on mh.id = mv.moviehall.id
                                INNER JOIN Cinema c on c.id = mh.cinema.id
                                WHERE c.id = ?1
                                GROUP BY cus.id, cus.firstname, cus.lastname
                                ORDER BY totalPrice DESC
                                LIMIT 5
                            """, Map.class)
                    .setParameter(1, cinema.getId())
                    .getResultList());
        }
        return map;

    }
    private List<Map> getManagerTopMovies(int managerId) {
        Aggregation aggregation = newAggregation(
                // Stage 1: Match movies for a specific employee_id
                match(Criteria.where("employee").is(managerId)),

                // Stage 2: Lookup to join with Employee collection to get manager details
                lookup("Employee", "employee", "_id", "manager"),
                unwind("manager"),

                // Stage 3: Match manager roles
                match(Criteria.where("manager.roles.name").is("Manager")),

                // Stage 4: Lookup to join with MovieScreening collection
                lookup("MovieScreening", "_id", "movieId", "screenings"),
                unwind("$screenings"),

                // Stage 5: Lookup to join with Ticket collection
                lookup("Ticket", "screenings._id", "_id.screeningId", "tickets"),
                unwind("$tickets"),

                // Stage 6: Group by movieId and movieName
                group(fields().and("movieId", "$_id").and("movieName", "$name"))
                        .sum("tickets.price").as("totalPrice"),

                // Stage 7: Sort by totalPrice descending
                sort(Sort.Direction.DESC, "totalPrice"),

                // Stage 8: Project to reshape the output
                project()
                        .andExclude("_id")
                        .and("movieId").as("movieId")
                        .and("movieName").as("movieName")
                        .and("totalPrice").as("totalPrice")
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "Movie", Map.class);
        return results.getMappedResults();
    }
    public Map<String, Object> createReportNedimNoSql() {
        var query = new Query(Criteria.where("roles.name").is("Manager"));
        var managers = mongoTemplate.find(query, EmployeeDocument.class);
        Map<String, Object> map = new HashMap<>();
        for (var manager: managers) {
            map.put(manager.getFirstname() + " " + manager.getLastname(), getManagerTopMovies(manager.getId()));
        }
        return map;
    }
    @Transactional
    public Map<String, Object> createReportNedimSql() {
        List<Employee> managers = entityManager.createQuery("""
                SELECT e
                FROM Employee e
                JOIN e.roles r
                WHERE r.name = 'Manager'
                """, Employee.class).getResultList();
        Map<String, Object> map = new HashMap<>();
        for (Employee manager : managers) {
            map.put(manager.getFirstname() + " " + manager.getLastname(), entityManager
                    .createQuery("""
                            SELECT m.id AS movieId, m.name AS movieName, SUM(t.price) AS totalPrice
                            FROM Ticket t
                            JOIN MovieScreening ms ON t.screening = ms
                            JOIN Movie m ON ms.movie = m
                            JOIN Employee e ON m.manager = e
                            JOIN e.roles r
                            WHERE e.id = ?1 AND r.name = 'Manager'
                            GROUP BY m.id, m.name
                            ORDER BY totalPrice DESC
                                     """, Map.class)
                    .setParameter(1, manager.getId())
                    .getResultList());
        }
        return map;
    }

}
