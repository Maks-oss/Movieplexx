import {useLocation} from 'react-router-dom';
import React, {useState, useEffect} from "react";
import {Stack} from '@mui/material';
import {MovieDetailsCard} from "../components/movie/MovieDetailsCard";
import {MovieScreenings} from "../components/movie/MovieScreenings";
import {useNavigate} from "react-router-dom";
import { useUserContext } from '../../utils/MovieplexxContext';
import useApiService from "../../utils/ApiService";

function MovieScreeningsPage() {
    const movieId = localStorage.getItem("movieId")
    const [movieDetails, setMovieDetails] = useState(null);
    const { endpoints } = useUserContext();
    const navigation = useNavigate()
    const apiService = useApiService()
    const handleOnScreeningClick = (screening) => {
        localStorage.setItem("screeningInfo", JSON.stringify({
            hallId: screening.moviehall.id,
            movieScreening: screening
        }))
        navigation(`${screening.moviehall.type}${screening.moviehall.id}`)
    }
    useEffect(() => {
        apiService.fetchApi(`http://localhost:5433/movies/${movieId}`).then((data) => {
            setMovieDetails(data)
        })
    }, [endpoints, movieId])
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
