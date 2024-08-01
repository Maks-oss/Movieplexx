import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import {useMovieplexxContext} from "../../utils/MovieplexxContext";
import {useNavigate} from "react-router-dom";
import useApiService from "../../utils/ApiService";
import React, {useState} from 'react'

import {AuthErrorAlert} from "../components/AuthErrorAlert";


export default function SignInPage() {
    const {setUser, setAccessToken, setIsValidToken} = useMovieplexxContext()
    const [authError, setAuthError] = useState(null)
    const [openAuthErrorDialog, setOpenAuthErrorDialog] = useState(false);
    const navigation = useNavigate();
    const apiService = useApiService()
    const handleAuthErrorDialogClose = () => {
        setOpenAuthErrorDialog(false);
    };
    const handleSubmit = async (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const creds = {
            username: data.get('email'),
            password: data.get('password'),
        }
        console.log("Creds: " + JSON.stringify(creds))
        const response = await apiService.authenticate(creds);
        const json = await response.json();
        if (response.ok) {
            setUser(creds)
            setAccessToken(json.accessToken)
            setIsValidToken(true)
            navigation("/movies")
        } else {
            setAuthError(json.error)
            setOpenAuthErrorDialog(true)
        }
    };

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline/>
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
                    <LockOutlinedIcon/>
                </Avatar>
                <Typography component="h1" variant="h5">
                    Sign in
                </Typography>
                <Box component="form" onSubmit={handleSubmit} noValidate sx={{mt: 1}}>
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="email"
                        label="Email"
                        name="email"
                        autoComplete="email"
                        autoFocus
                    />
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        name="password"
                        label="Password"
                        type="password"
                        id="password"
                        autoComplete="current-password"
                    />
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{mt: 3, mb: 2}}
                    >
                        Sign In
                    </Button>
                    <Grid container>
                        <Grid item xs>
                            <Link href="#" variant="body2">
                                Forgot password?
                            </Link>
                        </Grid>
                        <Grid item>
                            <Link href="#" variant="body2">
                                {"Don't have an account? Sign Up"}
                            </Link>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
            <AuthErrorAlert errorMessage={authError}
                            open={openAuthErrorDialog}
                            handleClose={handleAuthErrorDialogClose}/>
        </Container>
    );
}
