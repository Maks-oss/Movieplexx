package com.example.backend.data.nosql;

import com.example.backend.data.sql.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Document("Movie")
public class MovieDocument {
    @Id
    private Integer id;

    private String name;

    private String image;

    private String description;

    private Integer runtime;

    private LocalDate releaseDate;

    private Integer ageRating;
    @JsonIgnore
    private List<ActorDocument> actors = new ArrayList<>();
    @JsonIgnore
    private List<DirectorDocument> directors = new ArrayList<>();

    @DocumentReference
    private EmployeeDocument employee;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ActorDocument> getActors() {
        return actors;
    }

    public void setActors(List<ActorDocument> actors) {
        this.actors = actors;
    }

    public List<DirectorDocument> getDirectors() {
        return directors;
    }

    public void setDirectors(List<DirectorDocument> directors) {
        this.directors = directors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(Integer ageRating) {
        this.ageRating = ageRating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public EmployeeDocument getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDocument employee) {
        this.employee = employee;
    }
}
