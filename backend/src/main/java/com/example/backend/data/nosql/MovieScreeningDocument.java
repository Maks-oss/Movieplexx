package com.example.backend.data.nosql;

import com.example.backend.data.sql.Movie;
import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.Objects;

@Document("MovieScreening")
public class MovieScreeningDocument {
    @Id
    private Integer id;

    private Integer movieId;

    @DocumentReference
    private MovieDocument movie;

    @DocumentReference
    private MovieHallDocument moviehall;

    @Column(name = "starttime")
    private Instant startTime;

    @Column(name = "endtime")
    private Instant endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public MovieDocument getMovie() {
        return movie;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public void setMovie(MovieDocument movie) {
        this.movie = movie;
    }

    public void setMovie(int movie) {
        this.movieId = movie;
    }

    public MovieHallDocument getMoviehall() {
        return moviehall;
    }

    public void setMoviehall(MovieHallDocument moviehall) {
        this.moviehall = moviehall;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof MovieScreeningDocument that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getMovie(), that.getMovie()) && Objects.equals(getMoviehall(), that.getMoviehall()) && Objects.equals(getStartTime(), that.getStartTime()) && Objects.equals(getEndTime(), that.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMovie(), getMoviehall(), getStartTime(), getEndTime());
    }

    @Override
    public String toString() {
        return "MovieScreening{" +
               "id=" + id +
               ", movie=" + movieId +
               ", moviehall=" + moviehall +
               ", startTime=" + startTime +
               ", endTime=" + endTime +
               '}';
    }
}
