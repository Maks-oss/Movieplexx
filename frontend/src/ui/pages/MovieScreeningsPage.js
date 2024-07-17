import {useLocation} from 'react-router-dom';
import React, {useState, useEffect} from "react";
import {Stack} from '@mui/material';
import {MovieDetailsCard} from "../components/movie/MovieDetailsCard";
import {MovieScreenings} from "../components/movie/MovieScreenings";
import {useNavigate} from "react-router-dom";
import { useMovieplexxContext } from '../../utils/MovieplexxContext';
import useApiService from "../../utils/ApiService";

function MovieScreeningsPage() {
    const {state} = useLocation()
    const [movieDetails, setMovieDetails] = useState(null);
    const { endpoints } = useMovieplexxContext();
    const navigation = useNavigate()
    const apiService = useApiService()
    const handleOnScreeningClick = (screening) => {
        navigation(`${screening.moviehall.type}${screening.moviehall.id}`, {
            state: {
                hallId: screening.moviehall.id,
                movieScreening: screening
            }
        })
    }
    useEffect(() => {
        apiService.fetchApi(`http://localhost:5433/movies/${state}`).then((data) => {
            setMovieDetails(data)
        })
    }, [endpoints, state])
    if (movieDetails) {
        return (
                <Stack direction="row" spacing={4} sx={{margin: '20px'}}>
                    <MovieDetailsCard movieInfo={movieDetails.movieInfo} movieActors={movieDetails.movieCast.actors}
                                      movieDirectors={movieDetails.movieCast.directors}/>
                    <MovieScreenings movieScreenings={movieDetails.movieScreenings}
                                     onScreeningClick={handleOnScreeningClick}/>
                </Stack>
        )
    }
}

export default MovieScreeningsPage
