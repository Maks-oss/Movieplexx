import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import Button from '@mui/material/Button';
import LinearProgress from '@mui/material/LinearProgress';
import Modal from '@mui/material/Modal';
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { fetchApi, generate, migrate } from '../../utils/ApiCalls';
import { useMovieplexxContext } from '../../utils/MovieplexxContext';
import { FormControl, MenuItem, Select } from '@mui/material';
import Avatar from '@mui/material/Avatar';
import Tooltip from '@mui/material/Tooltip';
import IconButton from '@mui/material/IconButton';
import Menu from '@mui/material/Menu';

const LoadingModal = ({ modalDesc, loading }) => {
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
                <Typography id="transition-modal-title" variant="h6" component="h2" sx={{ marginBottom: '10px' }}>
                    {modalDesc} data...
                </Typography>
                <LinearProgress />
            </div>
        </Modal>
    )
};

function MovieplexxAppBar() {
    const [loading, setLoading] = React.useState(false);
    const [modalDesc, setModalDesc] = React.useState("");
    const { user, setUser, logout} = useMovieplexxContext();
    const [customers, setCustomers] = React.useState([]);
    const [employees, setEmployees] = React.useState([]);

    const navigate = useNavigate();

    React.useEffect(() => {

        fetchApi('http://localhost:5433/customers')
            .then((data) => {
                setCustomers(data);
                setUser(data[0]);
            })
            .catch((error) => {
                console.error('Error fetching data:', error);
            });
        fetchApi('http://localhost:5433/employees')
            .then((data) => {
                setEmployees(data);
            })
            .catch((error) => {
                console.error('Error fetching data:', error);
            });


    }, [setUser]);

    const handleGenerateData = async () => {
        setLoading(true);
        setModalDesc("Generating ");
        try {
            const data = await generate();
            console.log("Returned data -> ", data);
        } catch (error) {
            console.error("Error generating data: ", error);
        } finally {
            setLoading(false);
            window.location.reload();
        }
    };

    const [anchorElUser, setAnchorElUser] = React.useState(null);
    const handleOpenUserMenu = (event) => {
        setAnchorElUser(event.currentTarget);
    };
    const handleCloseUserMenu = () => {
        setAnchorElUser(null);
    };
    const allUsers = [ ...employees, ...customers];
    const settings = ['Profile', 'Logout'];

    const pages = allUsers.includes(user) && user?.roles?.some(role => role.name === 'Manager')
        ? ['Movies', 'New movie', 'First report', 'Second report']
        : ['Movies'];

    return (
        <>
            <AppBar position="static">
                <Container maxWidth="xl">
                    <Toolbar disableGutters>
                        <Typography
                            variant="h6"
                            noWrap
                            component="a"
                            as={Link}
                            to={"/"}
                            sx={{
                                mr: 2,
                                display: { xs: 'none', md: 'flex' },
                                fontFamily: 'monospace',
                                fontWeight: 700,
                                letterSpacing: '.3rem',
                                color: 'inherit',
                                textDecoration: 'none',
                            }}
                        >
                            Movieplexx
                        </Typography>
                        <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
                            {pages.map((page) => (
                                <Button
                                    key={page}
                                    onClick={() => navigate("/" + page.replaceAll(' ', '').toLowerCase())}
                                    sx={{ my: 2, color: 'white', display: 'block' }}
                                >
                                    {page}
                                </Button>
                            ))}
                        </Box>

                        <Box sx={{ flexGrow: 0, display: { xs: 'none', md: 'flex' } }}>
                            <Button
                                key="generate"
                                onClick={handleGenerateData}
                                sx={{ my: 2, color: 'white', display: 'block', borderRadius: '8px', marginRight: '18px' }}
                            >
                                Generate Data
                            </Button>
                        </Box>
                        <Box sx={{ flexGrow: 0 }}>
                            <Tooltip title="Open settings">
                                <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                                    <Avatar>U</Avatar>
                                </IconButton>
                            </Tooltip>
                            <Menu
                                sx={{ mt: '45px' }}
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
                                        setting === 'Logout' ? logout : handleCloseUserMenu
                                    }>
                                        <Typography textAlign="center">{setting}</Typography>
                                    </MenuItem>
                                ))}
                            </Menu>
                        </Box>
                    </Toolbar>
                </Container>
            </AppBar>
            <LoadingModal modalDesc={modalDesc} loading={loading} />
        </>
    );
}

export default MovieplexxAppBar;
