package com.example.backend.implementation.movie;

public class MovieCast {
    private String actorName;
    private String directorName;
    private String movieName;

    public MovieCast(String actorName, String directorName, String movieName) {
        this.actorName = actorName;
        this.directorName = directorName;
        this.movieName = movieName;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
