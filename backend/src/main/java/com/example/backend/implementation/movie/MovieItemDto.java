package com.example.backend.implementation.movie;

public record MovieItemDto(
        int movieId,
        String name,

        String image,
        String releaseDate) {

}
