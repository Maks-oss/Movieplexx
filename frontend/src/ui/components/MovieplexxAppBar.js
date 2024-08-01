import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import Button from '@mui/material/Button';
import LinearProgress from '@mui/material/LinearProgress';
import Modal from '@mui/material/Modal';
import {useNavigate} from "react-router-dom";
import {useMovieplexxContext} from '../../utils/MovieplexxContext';
import {MenuItem} from '@mui/material';
import Avatar from '@mui/material/Avatar';
import Tooltip from '@mui/material/Tooltip';
import IconButton from '@mui/material/IconButton';
import Menu from '@mui/material/Menu';
import Stack from '@mui/material/Stack';

import PersonIcon from '@mui/icons-material/Person';
import useApiService from "../../utils/ApiService";

const LoadingModal = ({modalDesc, loading}) => {
    return (
        <Modal
            open={loading}
            aria-labelledby="loading-modal-title"
            aria-describedby="loading-modal-description"
            style={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center'
            }}
        >
            <div style={{backgroundColor: 'black', borderRadius: 5, padding: 20}}>
                <Typography id="transition-modal-title" variant="h6" component="h2" sx={{marginBottom: '10px'}}>
                    {modalDesc} data...
                </Typography>
                <LinearProgress/>
            </div>
        </Modal>
    )
};

function MovieplexxAppBar() {
    const [loading, setLoading] = React.useState(false);
    const [modalDesc, setModalDesc] = React.useState("");
    const {accessToken, user, setUser, logout} = useMovieplexxContext();
    // const [customers, setCustomers] = React.useState([]);
    // const [employees, setEmployees] = React.useState([]);
    const apiService = useApiService()
    const navigate = useNavigate();

    // React.useEffect(() => {
    //
    //     fetchApi('http://localhost:5433/customers')
    //         .then((data) => {
    //             setCustomers(data);
    //             setUser(data[0]);
    //         })
    //         .catch((error) => {
    //             console.error('Error fetching data:', error);
    //         });
    //     fetchApi('http://localhost:5433/employees')
    //         .then((data) => {
    //             setEmployees(data);
    //         })
    //         .catch((error) => {
    //             console.error('Error fetching data:', error);
    //         });
    //
    //
    // }, [setUser]);

    const handleGenerateData = async () => {
        setLoading(true);
        setModalDesc("Generating ");
        const generationResponse = await apiService.generate();
        setLoading(false);
        if (generationResponse === "generated") {
            window.location.reload();
        } else {
            console.error("Failed to generate:", generationResponse.message)
        }
    };

    const [anchorElUser, setAnchorElUser] = React.useState(null);
    const handleOpenUserMenu = (event) => {
        setAnchorElUser(event.currentTarget);
    };
    const handleCloseUserMenu = () => {
        setAnchorElUser(null);
    };
    const handleSignIn = () => {
        navigate("/login")
    }
    const handleUserLogout = () => {
        logout()
        handleCloseUserMenu()
    }
    // const allUsers = [...employees, ...customers];
    const settings = ['Profile', 'Logout'];

    const pages = ['Movies', 'New movie', 'First report', 'Second report']

    return (
        <>
            <AppBar position="static">
                <Container maxWidth="xl">
                    <Toolbar disableGutters>
                        <Typography
                            variant="h6"
                            noWrap
                            component="a"
                            // as={Link}
                            // to={"/"}
                            sx={{
                                mr: 2,
                                display: {xs: 'none', md: 'flex'},
                                fontFamily: 'monospace',
                                fontWeight: 700,
                                letterSpacing: '.3rem',
                                color: 'inherit',
                                textDecoration: 'none',
                            }}
                        >
                            Movieplexx
                        </Typography>
                        <Box sx={{flexGrow: 1, display: {xs: 'none', md: 'flex'}}}>
                            {pages.map((page) => (
                                <Button
                                    key={page}
                                    onClick={() => navigate("/" + page.replaceAll(' ', '').toLowerCase())}
                                    sx={{my: 2, color: 'white', display: 'block'}}
                                >
                                    {page}
                                </Button>
                            ))}
                        </Box>

                        <Box sx={{flexGrow: 0, display: {xs: 'none', md: 'flex'}}}>
                            <Button
                                key="generate"
                                onClick={handleGenerateData}
                                sx={{my: 2, color: 'white', display: 'block', borderRadius: '8px', marginRight: '18px'}}
                            >
                                Generate Data
                            </Button>
                        </Box>
                        <Box sx={{flexGrow: 0}}>
                            {
                                accessToken !== null
                                    ? <AuthorizedUser openUserMenu={handleOpenUserMenu} user={user}/>
                                    : <NonAuthorizedUser authorizeUser={handleSignIn}/>
                            }
                            <Menu
                                sx={{mt: '45px'}}
                                id="menu-appbar"
                                anchorEl={anchorElUser}
                                anchorOrigin={{
                                    vertical: 'top',
                                    horizontal: 'right',
                                }}
                                keepMounted
                                transformOrigin={{
                                    vertical: 'top',
                                    horizontal: 'right',
                                }}
                                open={Boolean(anchorElUser)}
                                onClose={handleCloseUserMenu}
                            >
                                {settings.map((setting) => (
                                    <MenuItem key={setting} onClick={
                                        setting === 'Logout' ? handleUserLogout : handleCloseUserMenu
                                    }>
                                        <Typography textAlign="center">{setting}</Typography>
                                    </MenuItem>
                                ))}
                            </Menu>
                        </Box>
                    </Toolbar>
                </Container>
            </AppBar>
            <LoadingModal modalDesc={modalDesc} loading={loading}/>
        </>
    );
}

const AuthorizedUser = ({openUserMenu, user}) => {
    return (
        <Tooltip title="Open settings">
            <IconButton onClick={openUserMenu} sx={{p: 0}}>
                <Avatar>{user.username.charAt(0).toUpperCase()}</Avatar>
            </IconButton>
        </Tooltip>
    )
}
const NonAuthorizedUser = ({authorizeUser}) => {
    return (
        <Button onClick={authorizeUser}>
            <Stack direction="row" spacing={1}>
                <PersonIcon/>
                <label>Sign In</label>
            </Stack>
        </Button>
    )
}
export default MovieplexxAppBar;
