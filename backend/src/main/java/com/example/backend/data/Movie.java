package com.example.backend.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue
    @Column(name = "movieid", nullable = false)
    private Integer id;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "image", length = Integer.MAX_VALUE)
    private String image;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "runtime")
    private Integer runtime;

    @Column(name = "releasedate")
    private LocalDate releaseDate;

    @Column(name = "agerating")
    private Integer ageRating;

    @ManyToMany(mappedBy = "movies")
    private Set<Actor> actors;

    @ManyToMany(mappedBy = "movies")
    private Set<Director> directors;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @JsonBackReference
    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }
    public void addActor(Actor actor) {
        this.actors.add(actor);
        actor.getMovies().add(this);
    }
    public void addDirector(Director director) {
        this.directors.add(director);
        director.getMovies().add(this);
    }
    @JsonBackReference
    public Set<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<Director> directors) {
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
}
