import React from 'react';
import { Card, CardContent, Typography, Box } from '@mui/material';
import { styled } from '@mui/system';
import { useLocation} from 'react-router-dom';
import {getTime} from "../../utils/Utils";

const Ticket = styled(Card)({
    maxWidth: 400,
    margin: '20px auto',
    border: '1px solid #ccc',
    borderRadius: '10px',
    overflow: 'hidden',
});

const TicketHeader = styled(Box)(({ theme }) => ({
    backgroundColor: theme.palette.primary,
    color: '#fff',
    padding: theme.spacing(2),
    textAlign: 'center',
}));

const TicketContent = styled(CardContent)(({ theme }) => ({
    padding: theme.spacing(2),
}));

const TicketDetails = styled(Box)(({ theme }) => ({
    display: 'flex',
    justifyContent: 'space-between',
    marginTop: theme.spacing(1),
}));


const TicketPage = () => {
    const {state} = useLocation()
    return (
        <Ticket>
            <TicketHeader>
                <Typography variant="h5">{state.movieName}</Typography>
            </TicketHeader>
            <TicketContent>
                <Typography variant="body1">Cinema: {state.cinemaBranch}</Typography>
                <Typography variant="body1">Hall: {state.movieHall}</Typography>
                <Typography variant="body1">Seats: {state.seats.join(', ')}</Typography>
                <Typography variant="body1">DateOfIssue: {state.dateOfIssue}</Typography>
                <Typography variant="body1">Time: {getTime(state.movieStartTime)}</Typography>
                <TicketDetails>
                    <Typography variant="body2">Price: ${state.price}</Typography>
                </TicketDetails>
            </TicketContent>
        </Ticket>
    );
};

export default TicketPage


