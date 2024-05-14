package com.example.backend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepo extends JpaRepository<TestObj, Long> {
}
