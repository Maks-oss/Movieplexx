package com.example.backend.data.request;
import java.time.LocalDate;
import java.util.List;

public record MovieInsertRequest (
        String name,
        String description,
        String image,
        int runTime,
        LocalDate releaseDate,
        int ageRating,
        Integer managerId,
        List<Integer> actorIds,
        List<Integer> directorIds
) {
}
