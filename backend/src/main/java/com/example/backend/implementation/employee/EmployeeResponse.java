package com.example.backend.implementation.employee;


import com.example.backend.data.sql.Movie;
import com.example.backend.data.sql.Role;

import java.util.Set;

public record EmployeeResponse (
        int employeeId,
        String firstName,
        String lastName,
        String email,
        Set<Role> roles
) {

}
