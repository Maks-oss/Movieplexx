package com.example.backend.data.nosql;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

//@Document
//@IdClass(MoviePromoId.class)
public class MoviePromo {

//    @Id
    private Movie movie;

    private Integer moviePromoId;

    private String title;

    private String description;

    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
