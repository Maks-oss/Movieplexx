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
    const ticket = JSON.parse(localStorage.getItem("ticketData"))
    return (
        <Ticket>
            <TicketHeader>
                <Typography variant="h5">{ticket.movieName}</Typography>
            </TicketHeader>
            <TicketContent>
                <Typography variant="body1">Cinema: {ticket.cinemaBranch}</Typography>
                <Typography variant="body1">Hall: {ticket.movieHall}</Typography>
                <Typography variant="body1">Seat: {ticket.seat}</Typography>
                <Typography variant="body1">DateOfIssue: {ticket.dateOfIssue}</Typography>
                <Typography variant="body1">Time: {getTime(ticket.movieStartTime)}</Typography>
                <TicketDetails>
                    <Typography variant="body2">Price: ${ticket.price}</Typography>
                </TicketDetails>
            </TicketContent>
        </Ticket>
    );
};

export default TicketPage


