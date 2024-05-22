import SeatSelection from "../components/seats/SeatSelection";
import {fetchApi} from "../../utils/ApiCalls";
import React, {useState, useEffect} from "react";
import { useLocation} from 'react-router-dom';

function SeatPickerPage() {
    const {state} = useLocation()
    const [seats, setSeats] = useState(null);
    useEffect(() => {
        fetchApi(`http://localhost:5433/seats/${state.hallId}`).then((data) => {
            setSeats(data)
        })
    }, [])
    if (seats) {
        return (
            <SeatSelection seats={seats} movieScreening={state.movieScreening}/>
        )
    }
}
export default SeatPickerPage
