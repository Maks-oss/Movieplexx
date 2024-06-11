package com.example.backend.implementation.employee;

import com.example.backend.data.Movie;
import com.example.backend.data.Role;

import java.util.Set;

public record EmployeeResponse (
        int employeeId,
        String firstName,
        String lastName,
        String email,
        Set<Role> roles,
        Set<Movie> movies
) {

}