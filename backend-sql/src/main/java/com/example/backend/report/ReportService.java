package com.example.backend.report;

import com.example.backend.data.Cinema;
import com.example.backend.data.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Map<String, Object> createReport() {
        var cinemas = entityManager.createQuery("SELECT c FROM Cinema c", Cinema.class).getResultList();
        Map<String, Object> map = new HashMap<>();
        for (Cinema cinema : cinemas) {
            map.put(cinema.getName(), entityManager
                    .createQuery("""
                                SELECT concat(cus.lastname, ' ', cus.firstname) AS customerName, SUM(t.price) AS totalPrice FROM Customer cus
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

    @Transactional
    public Map<String, Object> createReportNedim() {
        List<Employee> managers = entityManager.createQuery("""
                SELECT e
                FROM Employee e
                JOIN e.roles r
                WHERE r.name = 'Manager'
                """, Employee.class).getResultList();
        Map<String, Object> map = new HashMap<>();
        for (Employee manager : managers) {
            map.put(manager.getLastname() + " " + manager.getFirstname(), entityManager
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

//    @Transactional
//    public List<?> createReportNative(int cinemaId) {
//        return entityManager
//                .createNativeQuery("""
//                            SELECT concat(cus.lastname, ' ', cus.firstname) AS customerName, SUM(t.price) AS totalPrice FROM Customer cus
//                            INNER JOIN Ticket t on t.customerid = cus.customerid
//                            INNER JOIN MovieScreening mv on mv.screeningid = t.screeningid
//                            INNER JOIN MovieHall mh on mh.moviehallid = mv.moviehallid
//                            INNER JOIN Cinema c on c.cinemaid = mh.cinemaid
//                            WHERE c.cinemaid = ?1
//                            GROUP BY cus.customerid, cus.firstname, cus.lastname
//                            ORDER BY totalPrice DESC
//                            LIMIT 5
//                        """, Map.class)
//                .setParameter(1, cinemaId)
//                .getResultList();
//    }
}
