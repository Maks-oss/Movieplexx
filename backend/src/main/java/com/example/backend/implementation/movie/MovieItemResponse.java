package com.example.backend.implementation.movie;

public record MovieItemResponse(
        int movieId,
        String name,

        String image,
        String releaseDate
) {

}
