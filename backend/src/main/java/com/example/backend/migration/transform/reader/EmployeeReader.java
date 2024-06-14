package com.example.backend.migration.transform.reader;

import com.example.backend.data.sql.Actor;
import com.example.backend.data.sql.Employee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("employee_reader")
public class EmployeeReader extends SqlItemReader<Employee> {
    @Override
    public List<Employee> readItems() {
        return entityManager
                .createQuery("""
                             SELECT e FROM Employee e
                             JOIN FETCH e.roles
                             """, Employee.class)
                .getResultList();
    }
}
