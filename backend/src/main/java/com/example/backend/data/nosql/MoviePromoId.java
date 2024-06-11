package com.example.backend.data.nosql;

import java.io.Serializable;
import java.util.Objects;

public class MoviePromoId implements Serializable {
    private Movie movie;
    private Integer moviePromoId;

    public MoviePromoId() {
    }

    public MoviePromoId(Movie movie, Integer moviePromoId) {
        this.movie = movie;
        this.moviePromoId = moviePromoId;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Integer getMoviePromoId() {
        return moviePromoId;
    }

    public void setMoviePromoId(Integer moviePromoId) {
        this.moviePromoId = moviePromoId;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoviePromoId that = (MoviePromoId) o;
        return Objects.equals(movie, that.movie) &&
                Objects.equals(moviePromoId, that.moviePromoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, moviePromoId);
    }
}
