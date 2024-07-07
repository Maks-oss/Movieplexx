import React, {useState} from 'react';
import './Seats.css';
import {Button} from '@mui/material';
import Fade from '@mui/material/Fade';
import PaymentMethodModal from "../PaymentMethodModal";
import {createTicketRequest} from "../../../utils/ApiCalls";
import {useNavigate} from "react-router-dom";
import {groupSeatsByRow} from "../../../utils/Utils";
import {useMovieplexxContext} from '../../../utils/MovieplexxContext';

const SeatSelection = ({seats, movieScreening}) => {
    const [selectedSeat, setSelectedSeat] = useState(null);
    const [isConfirm, setIsConfirm] = React.useState(false);
    const [openModal, setOpenModal] = React.useState(false);
    const [confirm, setConfirm] = useState(false)
    const [selectedMethod, setSelectedMethod] = useState('');
    const {endpoints, user} = useMovieplexxContext();

    const rows = groupSeatsByRow(seats);
    const navigation = useNavigate();
    const handleConfirmClick = async () => {
        setConfirm(true)
        createTicketRequest(selectedMethod, {
            seatId: selectedSeat,
            movieScreening: movieScreening,
            userId: user.id,
            isEmployee: user.roles != null
        }, endpoints).then((data) => {
            navigation(`ticket${data.movieName}`, {
                state: data
            })
            setConfirm(false)
        })
    };
    const toggleSeatSelection = (seatId) => {
        console.log("Seat: " + seatId)
        setSelectedSeat(seatId);
        setIsConfirm(true)
    };


    const handleOpen = () => setOpenModal(true);
    const handleClose = () => {
        if (!confirm) setOpenModal(false);
    }

    return (
        <div className="container">
            <ul className="showcase">
                <li>
                    <div className="seat"></div>
                    <small>N/A</small>
                </li>
                <li>
                    <div className="seat vip"></div>
                    <small>VIP</small>
                </li>
                <li>
                    <div className="seat selected"></div>
                    <small>Selected</small>
                </li>
                <li>
                    <div className="seat occupied"></div>
                    <small>Occupied</small>
                </li>

            </ul>

            <div className="container">
                <div className="screen"/>
                {Object.entries(rows).map(([row, {rowSeats}], rowIndex) => (
                    <div className="row" key={rowIndex}>
                        {rowSeats.map((data, seatIndex) => (
                            <div
                                key={seatIndex}
                                className={`seat ${
                                    data.occupied ? 'occupied' :
                                        JSON.stringify(selectedSeat?.id) === JSON.stringify(data.id) ? 'selected'
                                            : data.type === 'vip' ? 'vip' : ''
                                }`}
                                onClick={() => !data.occupied && toggleSeatSelection(data)}
                            ></div>
                        ))}
                    </div>
                ))}
                {/*<label>*/}
                {/*    {`Selected seat row: ${selectedSeat?.row} number: ${selectedSeat?.number} price: ${selectedSeat?.price}`}*/}
                {/*</label>*/}
                <Fade in={isConfirm}>
                    <Button size="medium" color="secondary" variant="outlined"
                            sx={{flexShrink: 0, borderRadius: '20px', margin: '10px'}} onClick={handleOpen}>
                        Confirm
                    </Button>
                </Fade>
            </div>
            <PaymentMethodModal open={openModal} confirm={confirm} handleClose={handleClose}
                                onConfirmClick={handleConfirmClick} selectedMethod={selectedMethod}
                                setSelectedMethod={setSelectedMethod}/>
        </div>
    );
};

export default SeatSelection;
