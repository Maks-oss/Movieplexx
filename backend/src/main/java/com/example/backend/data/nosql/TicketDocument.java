package com.example.backend.data.nosql;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Objects;

@Document("Ticket")
public class TicketDocument {
    @Id
    private TicketId id;

    @Field("customerid")
    private Integer customerId;

    @Field("employeeId")
    private Integer employeeId;

    @Field("dateofissue")
    private LocalDate dateOfIssue;

    @Field("price")
    private Float price;

    // Getters and Setters
    public TicketId getId() {
        return id;
    }

    public void setId(TicketId id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
