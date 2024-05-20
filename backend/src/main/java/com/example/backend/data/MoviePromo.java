package com.example.backend.data;

import jakarta.persistence.*;

@Entity
@Table(name = "moviepromo")
public class MoviePromo {
    @Id
    @Column(name = "title", length = Integer.MAX_VALUE)
    private String title;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "movieid")
    private Movie movie;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "image")
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
