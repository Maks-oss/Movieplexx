import { useLocation} from 'react-router-dom';
import {fetchApi, fetchMovieDetails} from "../ApiCalls";
import React, {useState, useEffect} from "react";
import {Stack, Typography} from '@mui/material';
import {MovieDetailsCard} from "../components/movie/MovieDetailsCard";
import {MovieScreenings} from "../components/movie/MovieScreenings";
import {useNavigate} from "react-router-dom";


function MovieScreeningsPage() {
    const {state} = useLocation()
    const [movieDetails, setMovieDetails] = useState(null);
    const navigation = useNavigate()
    const handleOnScreeningClick = (movieHallId) => {
        navigation('seatPicker', {state: movieHallId})
    }
    useEffect(() => {
        fetchApi(`http://localhost:5433/movies/${state}`).then((data) => {
            setMovieDetails(data)
        })
    }, [])
    if (movieDetails) {
        return (
            <Stack direction="row" spacing={4} sx={{margin: '20px'}}>
                <MovieDetailsCard movieInfo={movieDetails.movieInfo} movieActors={movieDetails.movieActors}
                                  movieDirectors={movieDetails.movieDirectors}/>
                <MovieScreenings movieScreenings={movieDetails.movieScreenings} onScreeningClick={handleOnScreeningClick}/>
            </Stack>
        )
    }
}

export default MovieScreeningsPage
