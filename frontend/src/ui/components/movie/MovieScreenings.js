import {Card, CardContent, CardHeader} from '@mui/material';
import {Button, Stack} from '@mui/material';
import {getTime, groupMovieScreeningsByCinema} from "../../../utils/Utils";

export const MovieScreeningsCard = ({cinemaName, screenings, onScreeningClick}) => {
    return (
        <Card >
            <CardHeader title={`Movieplexx - ${cinemaName}`}/>
            <CardContent>
                <Stack spacing={2} direction="row" sx={{overflow:'auto'}}>
                    {screenings.map(screening => {
                        return <Button size="large" color="secondary" variant="outlined" sx={{ flexShrink: 0 }} onClick={() => onScreeningClick(screening)}>
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

