package com.example.backend.implementation.movie;
import java.time.LocalDate;
import java.util.List;

public record MovieInsertRequest (
        String name,
        String description,
        String image,
        int runTime,
        LocalDate releaseDate,
        int ageRating,
        List<Integer> actorIds,
        List<Integer> directorIds
) {
}
