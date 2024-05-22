import React, {useState} from 'react';
import './Seats.css';
import {Button} from '@mui/material';
import Fade from '@mui/material/Fade';
import PaymentMethodModal from "../PaymentMethodModal";
import {createTicketRequest} from "../../../utils/ApiCalls";
import {useNavigate} from "react-router-dom";
import {groupSeatsByRow, sleep} from "../../../utils/Utils";

const SeatSelection = ({seats, movieScreening}) => {
    const [selectedSeats, setSelectedSeats] = useState([]);
    const [isConfirm, setIsConfirm] = React.useState(false);
    const [openModal, setOpenModal] = React.useState(false);
    const [confirm, setConfirm] = useState(false)
    const [selectedMethod, setSelectedMethod] = useState('');

    const rows = groupSeatsByRow(seats);
    const navigation = useNavigate();
    const handleConfirmClick = async () => {
        setConfirm(true)
        createTicketRequest(selectedMethod, {
            seatsIds: selectedSeats,
            movieScreening: movieScreening
        }).then((data) => {
            navigation('/movies/screening/seatPicker/ticket', {
                state: data
            })
            setConfirm(false)
        })
    };
    const toggleSeatSelection = (seatId) => {
        const newSelectedSeats = [...selectedSeats];
        if (newSelectedSeats.includes(seatId)) {
            newSelectedSeats.splice(newSelectedSeats.indexOf(seatId), 1);
        } else {
            newSelectedSeats.push(seatId);
        }
        setSelectedSeats(newSelectedSeats);
        setIsConfirm(newSelectedSeats.length !== 0)
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
                    <div className="seat selected"></div>
                    <small>Selected</small>
                </li>
                <li>
                    <div className="seat occupied"></div>
                    <small>Occupied</small>
                </li>
            </ul>

            <div className="container">
                <div className="screen"></div>
                {Object.entries(rows).map(([row, {rowSeats}], rowIndex) => (
                    <div className="row" key={row}>
                        {rowSeats.map((data, seatIndex) => (
                            <div
                                key={seatIndex}
                                className={`seat ${
                                    data.occupied ? 'occupied' : selectedSeats.includes(data.id) ? 'selected' : ''
                                }`}
                                onClick={() => !data.occupied && toggleSeatSelection(data.id)}
                            ></div>
                        ))}
                    </div>
                ))}
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
