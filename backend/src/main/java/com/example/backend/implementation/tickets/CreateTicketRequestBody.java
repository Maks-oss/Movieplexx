package com.example.backend.implementation.tickets;

import com.example.backend.data.Customer;
import com.example.backend.data.Employee;
import com.example.backend.data.MovieScreening;

import java.util.List;

public record CreateTicketRequestBody(
        MovieScreening movieScreening,
        Customer customer,
        Employee employee,
        List<Integer> seatsIds
) {
}
