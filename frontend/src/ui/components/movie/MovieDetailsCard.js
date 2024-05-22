import React from 'react';
import { Typography } from '@mui/material';
import {Card, CardMedia, CardContent,CardActions, IconButton, Collapse, Box} from '@mui/material';
import { styled } from '@mui/material/styles';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';


const ExpandMore = styled(props => {
    const { expand, ...other } = props;
    return <IconButton {...other} />;
})(({ theme, expand }) => ({
    transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
    }),
}));

export const MovieDetailsCard = ({movieInfo, movieActors, movieDirectors}) => {
    const [expanded, setExpanded] = React.useState(false);
    const handleExpandClick = () => {
        setExpanded(!expanded);
    };
    return (
        <Card sx={{ maxWidth: '40%'}}>
            <CardMedia
                component="img"
                height="400"
                src={movieInfo.image}
                title=""
            />
            <CardContent>
                <Typography gutterBottom variant='h4' component='div'>
                    {movieInfo.name}
                </Typography>
                <Typography variant='body1' color='text.secondary'>
                    {movieInfo.description}
                </Typography>
            </CardContent>
            <CardActions disableSpacing>
                <ExpandMore
                    expand={expanded}
                    onClick={handleExpandClick}
                    aria-expanded={expanded}
                    aria-label='show more'
                >
                    <ExpandMoreIcon />
                </ExpandMore>
            </CardActions>
            <Collapse in={expanded} timeout='auto' unmountOnExit>
                <CardContent>
                    <Typography gutterBottom variant='subtitle1' component='div'>
                        Actors
                    </Typography>
                    <Typography variant='body2' color='text.secondary'>
                        {movieActors.map(item=>item.firstname + ' ' + item.lastname).join(', ')}
                    </Typography>
                    <Box my={1.5} />
                    <Typography gutterBottom variant='subtitle1' component='div'>
                        Directors
                    </Typography>
                    <Typography variant='body2' color='text.secondary'>
                        {movieDirectors.map(item=>item.firstname + ' ' + item.lastname).join(', ')}
                    </Typography>
                    <Box my={1.5} />
                    <Typography gutterBottom variant='subtitle1' component='div'>
                        ReleaseDate
                    </Typography>
                    <Typography variant='body2' color='text.secondary'>
                        {movieInfo.releaseDate}
                    </Typography>

                    <Box my={1.5} />
                    <Typography gutterBottom variant='subtitle1' component='div'>
                        AgeRating
                    </Typography>
                    <Typography variant='body2' color='text.secondary'>
                        {`${movieInfo.ageRating}+`}
                    </Typography>
                    <Box my={1.5} />
                    <Typography gutterBottom variant='subtitle1' component='div'>
                        Runtime
                    </Typography>
                    <Typography variant='body2' color='text.secondary'>
                        { `${Math.floor(movieInfo.runtime / 60)}h ${movieInfo.runtime % 60}m` }
                    </Typography>
                </CardContent>
            </Collapse>


        </Card>
    );
}

