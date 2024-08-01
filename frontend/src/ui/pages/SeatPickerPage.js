import SeatSelection from "../components/seats/SeatSelection";
import React, {useEffect, useState} from "react";
import {useLocation} from 'react-router-dom';
import {useUserContext} from "../../utils/MovieplexxContext";
import useApiService from "../../utils/ApiService";

function SeatPickerPage() {
    const screeningInfo = JSON.parse(localStorage.getItem("screeningInfo"))
    const [seats, setSeats] = useState(null);
    const apiService = useApiService()
    useEffect(() => {
        apiService.fetchApi(`http://localhost:5433/moviehalls/${screeningInfo.hallId}/screenings/${screeningInfo.movieScreening.id}/seats`, true)
            .then((data) => {
                setSeats(data)
            })
    }, [])

    if (seats) {
        return (
            <SeatSelection seats={seats} movieScreening={screeningInfo.movieScreening}/>
        )
    }

}

export default SeatPickerPage
