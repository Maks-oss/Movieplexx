package com.example.backend.implementation.movie;
import java.time.LocalDate;
import java.util.List;

public class MovieInsertRequest {
    private String name;
    private String description;
    private String image;
    private int runTime;
    private LocalDate releaseDate;
    private int ageRating;
    private List<Integer> actorIds;
    private List<Integer> directorIds;

    // Getters and setters
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(int ageRating) {
        this.ageRating = ageRating;
    }

    public List<Integer> getActorIds() {
        return actorIds;
    }

    public void setActorIds(List<Integer> actorsId) {
        this.actorIds = actorsId;
    }

    public List<Integer> getDirectorIds() {
        return directorIds;
    }

    public void setDirectorIds(List<Integer> directorsId) {
        this.directorIds = directorsId;
    }
}
