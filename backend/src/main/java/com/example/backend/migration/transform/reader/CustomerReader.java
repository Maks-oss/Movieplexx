package com.example.backend.migration.transform.reader;

import com.example.backend.data.sql.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("customer_reader")
public class CustomerReader extends SqlItemReader<Customer> {
    @Override
    public List<Customer> readItems() {
        return entityManager
                .createQuery("SELECT c FROM Customer c", Customer.class)
                .getResultList();
    }
}
