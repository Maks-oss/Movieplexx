import SeatSelection from "../components/seats/SeatSelection";
import React, {useEffect, useState} from "react";
import {useLocation} from 'react-router-dom';
import {useMovieplexxContext} from "../../utils/MovieplexxContext";
import useApiService from "../../utils/ApiService";

function SeatPickerPage() {
    const {state} = useLocation()
    const [seats, setSeats] = useState(null);
    const {endpoints} = useMovieplexxContext();
    const apiService = useApiService()
    useEffect(() => {
        apiService.fetchApi(`http://localhost:5433/moviehalls/${state.hallId}/screenings/${state.movieScreening.id}/seats`, true)
            .then((data) => {
                setSeats(data)
            })
    }, [endpoints, state])

    if (seats) {
        return (
            <SeatSelection seats={seats} movieScreening={state.movieScreening}/>
        )
    }

}

export default SeatPickerPage
