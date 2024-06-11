package com.example.backend.transformation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component
public class DataTransformation {
    @PersistenceContext
    private EntityManager entityManager;


}
