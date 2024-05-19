import {fetchMovies} from '../ApiCalls'
import React, {useState, useEffect} from "react";
import {CardActionArea, Card, CardContent, CardMedia, Typography, Grid} from '@mui/material';

const ListItem = ({image, title, releaseDate}) => {
    const alertClicked = () => {
        alert('You clicked the third ListGroupItem');
    };

    return (
        <Card>
            <CardActionArea onClick={alertClicked}>
                <CardMedia
                    component="img"
                    height="200"
                    src='https://via.placeholder.com/200'
                    title="Paella dish"
                />
                <CardContent>
                    <Typography gutterBottom variant="h5" component="div">
                        {title}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        {releaseDate}
                    </Typography>
                </CardContent>
            </CardActionArea>
        </Card>
    );
};

function MoviesListPage() {
    const [movies, setMovies] = useState(null);
    useEffect(() => {
        fetchMovies().then((data) => {
            setMovies(data)
        })
    }, [])
    if (movies) {
        return (
            <Grid container sx ={{padding: '10px'}} spacing={{ xs: 2, md: 3 }} columns={{ xs: 4, sm: 8, md: 12 }} margin={8}>
                {movies.map((item, index) => (
                    <Grid item xs={4} sm={4} md={3} key={index}>
                        <ListItem image={item.image} title={item.name}
                                  releaseDate={'Release date: ' + item.releaseDate}/>
                    </Grid>
                ))}
            </Grid>
        );
    }
}

export default MoviesListPage;
