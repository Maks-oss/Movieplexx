package com.example.backend.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "moviescreening")
public class MovieScreening {
    @Id @GeneratedValue
    @Column(name = "screeningid", nullable = false)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "movieid")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "moviehallid")
    private MovieHall moviehall;

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
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public MovieHall getMoviehall() {
        return moviehall;
    }

    public void setMoviehall(MovieHall moviehall) {
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
        if (!(object instanceof MovieScreening that)) return false;
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
               ", movie=" + movie +
               ", moviehall=" + moviehall +
               ", startTime=" + startTime +
               ", endTime=" + endTime +
               '}';
    }
}
