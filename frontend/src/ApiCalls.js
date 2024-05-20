export async function fetchMovies() {
    const response = await fetch("http://localhost:5433/movies");
    const movies = await response.json();
    // console.log(movies);
    return movies
}

export async function fetchMovieDetails(movieId) {
    const response = await fetch(`http://localhost:5433/movies/${movieId}`);
    const movieDetail = await response.json();
    console.log(movieDetail);
    return movieDetail
}

export async function fetchMovieHallSeats(movieHallId) {
    const response = await fetch(`http://localhost:5433/movies/${movieHallId}`);
    const movieDetail = await response.json();
    console.log(movieDetail);
    return movieDetail
}
export async function fetchApi(endpoint) {
    const response = await fetch(endpoint);
    const data = await response.json();
    console.log(data);
    return data
}
