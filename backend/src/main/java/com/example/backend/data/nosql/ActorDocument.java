package com.example.backend.data.nosql;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document("Actor")
public class ActorDocument {
    @Id
    private Integer id;

    private String firstname;

    private String lastname;
    @DocumentReference
    private List<MovieDocument> movies = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieDocument> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDocument> movies) {
        this.movies = movies;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void addMovie(MovieDocument movie) {
        this.movies.add(movie);
    }
}
