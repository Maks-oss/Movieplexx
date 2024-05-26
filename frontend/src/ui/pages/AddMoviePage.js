import React, { useState, useEffect } from 'react'
import { Box, Button, Chip, FormLabel, MenuItem, OutlinedInput, Select, TextField, Typography } from "@mui/material";
import { addMovie, fetchApi } from "../../utils/ApiCalls";


const AddMoviePage = () => {
    const [insertMovie, setinsertMovie] = useState({ name: "", description: "", image: "", runTime: 0, releaseDate: "", ageRating: 0 });
    const [actors, setActors] = useState([]);
    const [directors, setDirectors] = useState([]);
    const [actorsId, setActorsId] = useState([]);
    const [directorsId, setDirectorsId] = useState([]);

    useEffect(() => {
        fetchApi(`http://localhost:5433/actors`).then((data) => {
            setActors(data);
        })
        fetchApi(`http://localhost:5433/directors`).then((data) => {
            setDirectors(data);
        })
    }, [])
    const handleChange = (e) => {
        setinsertMovie((prevState) => ({ ...prevState, [e.target.name]: e.target.value }));
    }
    const handleSubmit = (e) => {
        e.preventDefault();
        let actorIds = [];
        let directorIds = [];

        actorsId.forEach(element => {
            actorIds.push(element.id);
        });
        directorsId.forEach(element => {
            directorIds.push(element.id);
        });
        const movieData = {
            ...insertMovie,
            actorIds,
            directorIds
        };
        console.log(movieData);
        addMovie(movieData).then((data) => {
            console.log("Returned data -> ");
            console.log(data);
        });

    }
    const handleActorChange = (e) => {
        setActorsId(e.target.value);
    }

    const handleDirectorChange = (e) => {
        setDirectorsId(e.target.value);
    }

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <Box width={'50%'} padding={7} margin="auto" display={'flex'} flexDirection={'column'} >
                    <Typography textAlign={'center'} variant='h5' fontFamily={"verdana"}>
                        Add New Movie
                    </Typography>
                    <FormLabel>Name</FormLabel>
                    <TextField value={insertMovie.name} onChange={handleChange} name="name" variant="standard" margin="normal" />
                    <FormLabel>Description</FormLabel>
                    <TextField value={insertMovie.description} onChange={handleChange} name="description" variant="standard" margin="normal" />
                    <FormLabel>Image</FormLabel>
                    <TextField value={insertMovie.image} onChange={handleChange} name="image" variant="standard" margin="normal" />
                    <FormLabel>Runtime</FormLabel>
                    <TextField value={insertMovie.runTime} onChange={handleChange} name="runTime" variant="standard" margin="normal" />
                    <FormLabel>Release Date</FormLabel>
                    <TextField sx={{ svg: { color: '#fff' }, input: { color: '#fff' } }} type="date" value={insertMovie.releaseDate} onChange={handleChange} name="releaseDate" variant="standard" margin="normal" />
                    <FormLabel>Age rating</FormLabel>
                    <TextField value={insertMovie.ageRating} onChange={handleChange} name="ageRating" variant="standard" margin="normal" />

                    <FormLabel>Add Actors</FormLabel>
                    <Box display={"flex"}>
                        <Select
                            fullWidth
                            multiple
                            value={actorsId}
                            onChange={handleActorChange}
                            input={<OutlinedInput id="select-multiple-chip" label="Chip" />}
                            renderValue={(selected) => (
                                selected.map((value) => (
                                    <Box key={value.id} sx={{ display: 'flex', flexWrap: 'wrap', gap: 1, marginTop: '3px' }}>
                                        <Chip key={value.id} label={`${value.firstname} ${value.lastname}`} />
                                    </Box>
                                ))
                            )}
                        >
                            {actors.map((item) => (
                                <MenuItem key={item.id} value={item}>
                                    {`${item.firstname} ${item.lastname}`}
                                </MenuItem>
                            ))}
                        </Select>
                    </Box>
                    <FormLabel>Add Directors</FormLabel>
                    <Box display={"flex"}>
                        <Select
                            fullWidth
                            multiple
                            value={directorsId}
                            onChange={handleDirectorChange}
                            input={<OutlinedInput id="select-multiple-chip" label="Chip" />}
                            renderValue={(selected) => (
                                selected.map((value) => (
                                    <Box key={value.id} sx={{ display: 'flex', flexWrap: 'wrap', gap: 1, marginTop: '3px' }}>
                                        <Chip key={value.id} label={`${value.firstname} ${value.lastname}`} />
                                    </Box>
                                ))
                            )}
                        >
                            {directors.map((item) => (
                                <MenuItem key={item.id} value={item}>
                                    {`${item.firstname} ${item.lastname}`}
                                </MenuItem>
                            ))}
                        </Select>
                    </Box>

                    <Button type="submit" variant="contained" sx={{ width: "30%", margin: "auto", marginTop: "15px" }}> Add New Movie</Button>

                </Box>
            </form>
        </div>
    )
}

export default AddMoviePage