package com.example.backend.migration.transform.reader;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class SqlItemReader<T> implements ItemReader<T>{
    @PersistenceContext
    protected EntityManager entityManager;
}
