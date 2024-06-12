package com.example.backend.data.sql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "actor")
public class Actor {
    @Id
    @GeneratedValue
    @Column(name = "actorid", nullable = false)
    private Integer id;

    @Column(name = "firstname", length = Integer.MAX_VALUE)
    private String firstname;

    @Column(name = "lastname", length = Integer.MAX_VALUE)
    private String lastname;
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "actormovie",
            joinColumns = @JoinColumn(name = "actorid"),
            inverseJoinColumns = @JoinColumn(name = "movieid"))
    private Set<Movie> movies = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
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

    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }

    @Override
    public String toString() {
        return "Actor{" +
               "id=" + id +
               ", firstname='" + firstname + '\'' +
               ", lastname='" + lastname + '\'' +
               ", movies=" + movies +
               '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Actor actor)) return false;
        return Objects.equals(getId(), actor.getId()) && Objects.equals(getFirstname(), actor.getFirstname()) && Objects.equals(getLastname(), actor.getLastname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstname(), getLastname());
    }
}
