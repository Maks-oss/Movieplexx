package com.example.backend.migration.transform.reader;

import com.example.backend.data.sql.Employee;
import com.example.backend.data.sql.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("role_reader")
public class RoleReader extends SqlItemReader<Role> {
    @Override
    public List<Role> readItems() {
        return entityManager
                .createQuery("""
                             SELECT r FROM Role r
                             JOIN FETCH r.employees
                             """, Role.class)
                .getResultList();
    }
}
