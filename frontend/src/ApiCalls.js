export async function fetchMovies() {
    const response = await fetch("http://localhost:5433/movies");
    const movies = await response.json();
    console.log(movies);
    return movies
}

