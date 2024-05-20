export async function fetchMovies() {
    const response = await fetch("http://localhost:5433/movies");
    const movies = await response.json();
    // console.log(movies);
    return movies
}

export async function fetchMovieDetails(id) {
    const response = await fetch(`http://localhost:5433/movies/${id}`);
    const movieDetail = await response.json();
    console.log(movieDetail);
    return movieDetail
}
