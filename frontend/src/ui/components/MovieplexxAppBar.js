import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import Button from '@mui/material/Button';
import CircularProgress from '@mui/material/CircularProgress';
import Modal from '@mui/material/Modal';
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { fetchApi, generate } from '../../utils/ApiCalls';
import { useUserContext } from '../../utils/UserContext';


function MovieplexxAppBar() {
    const [loading, setLoading] = React.useState(false);
    const { user, setUser } = useUserContext();
    const [customer, setCustomer] = React.useState(null);
    const [manager, setManager] = React.useState(null);

    const navigate = useNavigate();

    React.useEffect(() => {

        fetchApi('http://localhost:5433/customers')
            .then((data) => {
                setCustomer(data[0]);
                setUser(data[0]);
            })
            .catch((error) => {
                console.error('Error fetching data:', error);
            });
        fetchApi('http://localhost:5433/employees')
            .then((data) => {
                data.forEach(element => {
                    element.roles.forEach(role => {
                        if (role.name === 'Manager')
                            setManager(element);
                    });
                });
            })
            .catch((error) => {
                console.error('Error fetching data:', error);
            });


    }, [setUser]);

    const handleGenerateData = async () => {
        setLoading(true);
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

    const handleToggleUserType = () => {
        setUser(prevType => prevType === customer ? manager : customer);
    };

    const pages = user === manager
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

                        <Box sx={{ flexGrow: 0, display: { xs: 'none', md: 'flex' } }}>
                            <Button onClick={handleToggleUserType}
                                sx={{ my: 2, color: 'white', display: 'block', borderRadius: '8px', marginRight: '18px' }}>
                                Toggle User
                            </Button>
                        </Box>
                    </Toolbar>
                </Container>
            </AppBar>
            <Modal
                open={loading}
                aria-labelledby="loading-modal-title"
                aria-describedby="loading-modal-description"
                style={{
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                }}
            >
                <CircularProgress />
            </Modal>
        </>
    );
}

export default MovieplexxAppBar;
