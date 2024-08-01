import React, { useState, useEffect } from 'react'
import { Box, Button, Chip, FormLabel, MenuItem, Modal, OutlinedInput, Select, TextField, Typography } from "@mui/material";
import { useNavigate } from 'react-router-dom';
import { useMovieplexxContext } from '../../utils/MovieplexxContext';
import useApiService from "../../utils/ApiService";


const AddMoviePage = () => {
    const [insertMovie, setinsertMovie] = useState({ name: "", description: "", image: "", runTime: "", releaseDate: "", ageRating: "", managerId: "" });
    const [actors, setActors] = useState([]);
    const [directors, setDirectors] = useState([]);
    const [actorsId, setActorsId] = useState([]);
    const [directorsId, setDirectorsId] = useState([]);
    const [success, setSuccess] = useState(false);
    const { user, endpoints } = useMovieplexxContext();
    const apiService = useApiService();
    const navigation = useNavigate();

    useEffect(() => {
        apiService.fetchApi(`http://localhost:5433/actors`).then((data) => {
            setActors(data);
        })
        apiService.fetchApi(`http://localhost:5433/directors`).then((data) => {
            setDirectors(data);
        })
    }, [endpoints])

    const handleCloseSuccessModal = () => {
        setSuccess(false);
        navigation("/movies"); // Redirect to movies page
    }

    const handleChange = (e) => {
        if (e.target.name === "runTime" || e.target.name === "ageRating") {
            const re = /^[0-9\b]+$/;
            if (e.target.value === '' || re.test(e.target.value)) {
                setinsertMovie((prevState) => ({ ...prevState, [e.target.name]: e.target.value }));
            }
            else {
                setinsertMovie((prevState) => ({ ...prevState, [e.target.name]: "" }));
            }
            return;
        }
        setinsertMovie((prevState) => ({ ...prevState, [e.target.name]: e.target.value }));
    }
    const handleSubmit = async (e) => {
        e.preventDefault();
        let actorIds = [];
        let directorIds = [];
        insertMovie.managerId = user ? user.id : '';
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
        apiService.addMovie(movieData, endpoints)
            .then((data) => {
                setSuccess(true);
            })
            .catch((error) => {
                console.error("Error adding movie: ", error);
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
            <form onSubmit={handleSubmit} >
                <Box width={'50%'} padding={7} margin="auto" display={'flex'} flexDirection={'column'}  >
                    <Typography textAlign={'center'} variant='h5' fontFamily={"verdana"}>
                        Add New Movie
                    </Typography>
                    <FormLabel>Name</FormLabel>
                    <TextField required value={insertMovie.name} onChange={handleChange} name="name" variant="standard" margin="normal" />
                    <FormLabel>Description</FormLabel>
                    <TextField required value={insertMovie.description} onChange={handleChange} name="description" variant="standard" margin="normal" />
                    <FormLabel>Image</FormLabel>
                    <TextField required value={insertMovie.image} onChange={handleChange} name="image" variant="standard" margin="normal" />
                    <FormLabel>Runtime</FormLabel>
                    <TextField required value={insertMovie.runTime} onChange={handleChange} name="runTime" variant="standard" margin="normal" />
                    <FormLabel>Release Date</FormLabel>
                    <TextField required
                        sx={{ 'input[type="date"]::-webkit-calendar-picker-indicator': { filter: 'invert(1)' } }}
                        type="date"
                        value={insertMovie.releaseDate}
                        onChange={handleChange}
                        name="releaseDate"
                        variant="standard"
                        margin="normal"
                        placeholder='Pick a release date' />

                    <FormLabel>Age rating</FormLabel>
                    <TextField required value={insertMovie.ageRating} onChange={handleChange} name="ageRating" variant="standard" margin="normal" />

                    <FormLabel>Add Actors</FormLabel>
                    <Box display={"flex"}>
                        <Select required
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
                        <Select required
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
            <Modal
                open={success}
                onClose={handleCloseSuccessModal}
                aria-labelledby="success-modal-title"
                aria-describedby="success-modal-description"
                style={{
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                }}
            >
                <Box sx={{
                    bgcolor: 'white',
                    p: 3,
                    borderRadius: "8px",
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    flexDirection: "column"
                }}>
                    <Typography variant="h6" id="success-modal-title" align="center" sx={{ color: "black" }}>
                        Movie Added Successfully
                    </Typography>
                    <Button variant="contained" onClick={handleCloseSuccessModal} size='small' >
                        OK
                    </Button>
                </Box>
            </Modal>
        </div>
    )
}

export default AddMoviePage
