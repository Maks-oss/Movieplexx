import SeatSelection from "../components/seats/SeatSelection";
import {fetchApi} from "../../utils/ApiCalls";
import React, {useState, useEffect} from "react";
import { useLocation} from 'react-router-dom';
import { useMovieplexxContext } from "../../utils/MovieplexxContext";

function SeatPickerPage() {
    const {state} = useLocation()
    const [seats, setSeats] = useState(null);
    const { endpoints } = useMovieplexxContext();
    useEffect(() => {
        fetchApi(`http://localhost:5433/moviehall/${state.hallId}/screening/${state.movieScreening.id}/seats`).then((data) => {
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
