import {useLocation} from 'react-router-dom';
import {fetchApi} from "../../utils/ApiCalls";
import React, {useState, useEffect} from "react";
import {Stack} from '@mui/material';
import {MovieDetailsCard} from "../components/movie/MovieDetailsCard";
import {MovieScreenings} from "../components/movie/MovieScreenings";
import {useNavigate} from "react-router-dom";
import { useMovieplexxContext } from '../../utils/MovieplexxContext';


function MovieScreeningsPage() {
    const {state} = useLocation()
    const [movieDetails, setMovieDetails] = useState(null);
    const { endpoints } = useMovieplexxContext();
    const navigation = useNavigate()
    const handleOnScreeningClick = (screening) => {
        navigation(`${screening.moviehall.type}${screening.moviehall.id}`, {
            state: {
                hallId: screening.moviehall.id,
                movieScreening: screening
            }
        })
    }
    useEffect(() => {
        fetchApi(`http://localhost:5433${endpoints.getMovies}/${state}`).then((data) => {
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
