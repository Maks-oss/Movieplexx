import {Card, CardMedia, CardContent, CardHeader, IconButton, Collapse} from '@mui/material';
import {Button, Stack, Typography, Box} from '@mui/material';

export const MovieScreeningsCard = ({cinemaName, screenings, onScreeningClick}) => {
    return (
        <Card >
            <CardHeader title={`Movieplexx - ${cinemaName}`}/>
            <CardContent>
                <Stack spacing={2} direction="row" sx={{overflow:'auto'}}>
                    {screenings.map(screening => {
                        return <Button size="large" color="secondary" variant="outlined" sx={{ flexShrink: 0 }} onClick={() => onScreeningClick(screening.moviehall.id)}>
                            {getTime(screening.startTime)}<br/>
                            {screening.moviehall.type + ' ' + screening.moviehall.number}
                        </Button>
                    })}
                </Stack>
            </CardContent>
        </Card>
    )
}

export const MovieScreenings = ({movieScreenings, onScreeningClick}) => {
    const cinemasScreenings = groupMovieScreeningsByCinema(movieScreenings)
    return (
        <Stack spacing={2} sx={{maxWidth: '60%', height: '100%'}}>
            {Object.entries(cinemasScreenings).map(([cinemaId, {cinema, screenings}]) => {
                return <MovieScreeningsCard cinemaName={cinema.name} screenings={screenings} onScreeningClick={onScreeningClick}/>
            })}
        </Stack>
    )
}


function groupMovieScreeningsByCinema(movieScreenings) {
    return movieScreenings.reduce((acc, screening) => {
        const cinemaId = screening.moviehall.cinema.id;
        if (!acc[cinemaId]) {
            acc[cinemaId] = {
                cinema: screening.moviehall.cinema,
                screenings: []
            };
        }
        acc[cinemaId].screenings.push({
            moviehall: screening.moviehall,
            startTime: screening.startTime,
            endTime: screening.endTime
        });
        return acc;
    }, {});
}
function getTime(timestamp) {
    const date = new Date(timestamp);
    let utcHours = date.getUTCHours();
    let utcMinutes = date.getUTCMinutes();
    return (utcHours < 10 ? `0${utcHours}` : utcHours)
        + ':' +
        (utcMinutes < 10 ? `0${utcMinutes}` : utcMinutes);
}
