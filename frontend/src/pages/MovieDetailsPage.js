import {useParams} from 'react-router-dom';
import {fetchMovieDetails} from "../ApiCalls";
import React, {useState, useEffect} from "react";
import {Stack, Typography} from '@mui/material';
import {MovieDetailsCard} from "../components/MovieDetailsCard";
import {MovieScreenings} from "../components/MovieScreenings";


function MovieDetailsPage() {
    const {itemId} = useParams()
    const [movieDetails, setMovieDetails] = useState(null);
    useEffect(() => {
        fetchMovieDetails(itemId).then((data) => {
            setMovieDetails(data)
        })
    }, [])
    if (movieDetails) {
        return (
            <Stack direction="row" spacing={4} sx={{ margin: '20px' }}>
                <MovieDetailsCard movieInfo={movieDetails.movieInfo} movieActors={movieDetails.movieActors}
                                  movieDirectors={movieDetails.movieDirectors}/>
                <MovieScreenings movieScreenings={movieDetails.movieScreenings}/>
            </Stack>
        )
    }
}

export default MovieDetailsPage
