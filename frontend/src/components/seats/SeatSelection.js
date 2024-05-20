import React, { useState } from 'react';
import './Seats.css';
import {Button} from '@mui/material';
import Fade from '@mui/material/Fade';
import Modal from '@mui/material/Modal';
import Backdrop from '@mui/material/Backdrop';
import {Box, Typography} from '@mui/material';
import LinearProgress from '@mui/material/LinearProgress';
import PaymentMethodModal from "../PaymentMethodModal";

const SeatSelection = ({seats}) => {
    const [selectedSeats, setSelectedSeats] = useState([]);
    const [price, setPrice] = useState(10); // Assuming each seat costs $10
    const [isConfirm, setIsConfirm] = React.useState(false);
    const [openModal, setOpenModal] = React.useState(false);


    const rows = groupSeatsByRow(seats);

    const toggleSeatSelection = (rowIndex, seatIndex) => {
        const newSelectedSeats = [...selectedSeats];
        const seatIdentifier = `${rowIndex}-${seatIndex}`;
        if (newSelectedSeats.includes(seatIdentifier)) {
            newSelectedSeats.splice(newSelectedSeats.indexOf(seatIdentifier), 1);
        } else {
            newSelectedSeats.push(seatIdentifier);
        }
        setSelectedSeats(newSelectedSeats);
        setIsConfirm(newSelectedSeats.length !==0)
    };

    const calculateTotalPrice = () => {
        return selectedSeats.length * price;
    };

    const handleOpen = () => setOpenModal(true);
    const handleClose = () => setOpenModal(false);

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
                                    data.occupied ? 'occupied' : selectedSeats.includes(`${rowIndex}-${seatIndex}`) ? 'selected' : ''
                                }`}
                                onClick={() => !data.occupied && toggleSeatSelection(rowIndex, seatIndex)}
                            ></div>
                        ))}
                    </div>
                ))}
            </div>
            <Fade in={isConfirm}>
                <Button size="medium" color="secondary" variant="outlined" sx={{ flexShrink: 0, borderRadius: '20px', margin: '10px' }} onClick={handleOpen} >
                    Confirm
                </Button>
            </Fade>
            <PaymentMethodModal open={openModal} handleClose={handleClose}/>
        </div>
    );
};
function groupSeatsByRow(seats) {
    return seats.reduce((acc, seat) => {
        const row = seat.row;
        if (!acc[row]) {
            acc[row] = {
                rowSeats: []
            };
        }
        acc[row].rowSeats.push({
            number: seat.number,
            type: seat.type,
            occupied: seat.ticket !== null
        });
        return acc;
    }, {});
}

export default SeatSelection;
