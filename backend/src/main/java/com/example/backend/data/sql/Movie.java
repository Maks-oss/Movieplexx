package com.example.backend.data.sql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;


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
    @JsonIgnore
    @ManyToMany(mappedBy = "movies")
    private Set<Actor> actors;
    @JsonIgnore
    @ManyToMany(mappedBy = "movies")
    private Set<Director> directors;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "managerid")
    private Employee manager;

    @ManyToOne
    @JoinColumn(name = "employeeid")
    private Employee employee;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Actor> getActors() {
        return actors;
    }


    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public Set<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<Director> directors) {
        this.directors = directors;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
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

    @Override
    @Transactional
    public String toString() {
        return "Movie{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", image='" + image + '\'' +
               ", description='" + description + '\'' +
               ", runtime=" + runtime +
               ", releaseDate=" + releaseDate +
               ", ageRating=" + ageRating +
               ", actors=" + actors +
               ", directors=" + directors +
               ", employee=" + employee +
               '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Movie movie)) return false;
        return Objects.equals(getId(), movie.getId()) && Objects.equals(getName(), movie.getName()) && Objects.equals(getImage(), movie.getImage()) && Objects.equals(getDescription(), movie.getDescription()) && Objects.equals(getRuntime(), movie.getRuntime()) && Objects.equals(getReleaseDate(), movie.getReleaseDate()) && Objects.equals(getAgeRating(), movie.getAgeRating()) && Objects.equals(employee, movie.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getImage(), getDescription(), getRuntime(), getReleaseDate(), getAgeRating(), employee);
    }
}
