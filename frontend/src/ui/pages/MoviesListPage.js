import React, {useState, useEffect} from "react";
import {CardActionArea, Card, CardContent, CardMedia, Typography, Grid} from '@mui/material';
import { useNavigate } from "react-router-dom";
import {useNavigationParamsContext, useUserContext} from '../../utils/MovieplexxContext';
import useApiService from "../../utils/ApiService";

const ListItem = ({ image, title, releaseDate, itemId}) => {
    const navigation = useNavigate();
    const navigationParams = useNavigationParamsContext()
    const onMovieItemClicked = () => {
        localStorage.setItem("movieId", itemId)
        navigation(`${title}${itemId}`)
    };
    return (
        <Card>
            <CardActionArea onClick={onMovieItemClicked}>
                <CardMedia
                    component="img"
                    height="200"
                    src={image}
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
    const { endpoints } = useUserContext();
    const apiService = useApiService()

    useEffect(() => {
        apiService.fetchApi(`http://localhost:5433/movies`).then((data) => {
            setMovies(data)
        })
    }, [endpoints])

    if (movies) {
        return (
            <Grid container sx={{padding: '20px'}} spacing={{ xs: 2, md: 3 }} columns={{ xs: 4, sm: 8, md: 12 }} margin={8}>
                {movies.map((item, index) => (
                    <Grid item xs={4} sm={4} md={3} key={index}>
                        <ListItem image={item.image} title={item.name} itemId={item.movieId}
                                  releaseDate={'Release date: ' + item.releaseDate}/>
                    </Grid>
                ))}
            </Grid>
        );
    }
}

export default MoviesListPage;
