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
        //seats/hall/1/screening/1
        fetchApi(`http://localhost:5433/seats/hall/${state.hallId}/screening/${state.movieScreening.id}`).then((data) => {
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
