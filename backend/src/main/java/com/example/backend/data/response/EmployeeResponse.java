package com.example.backend.data.response;


import com.example.backend.data.persistence.Role;

import java.util.Set;

public record EmployeeResponse (
        int employeeId,
        String firstName,
        String lastName,
        String email,
        Set<Role> roles
) {

}
