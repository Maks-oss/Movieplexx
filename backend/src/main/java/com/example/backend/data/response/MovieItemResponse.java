package com.example.backend.data.response;

public record MovieItemResponse(
        int movieId,
        String name,
        String image,
        String releaseDate
) {

}
