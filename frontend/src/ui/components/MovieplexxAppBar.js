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
import { FormControl, InputLabel, MenuItem, Select } from '@mui/material';

const LoadingModal = ({ modalDesc, loading }) => {

    return (
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
            <div>
                <Typography id="transition-modal-title" variant="h6" component="h2" sx={{ marginBottom: '10px' }}>
                    {modalDesc} data...
                </Typography>
                <LinearProgress />
            </div>
        </Modal>
    )
};

function MovieplexxAppBar() {
    const [isClicked, setIsClicked] = React.useState(false);
    const [loading, setLoading] = React.useState(false);
    const [modalDesc, setModalDesc] = React.useState("");
    const { user, setUser, endpoints, setEndpoints } = useMovieplexxContext();
    const [customers, setCustomers] = React.useState([]);
    const [employees, setEmployees] = React.useState([]);

    const navigate = useNavigate();

    React.useEffect(() => {

        fetchApi('http://localhost:5433' + endpoints.getCustomers)
            .then((data) => {
                setCustomers(data);
                setUser(data[0]);
            })
            .catch((error) => {
                console.error('Error fetching data:', error);
            });
        fetchApi('http://localhost:5433' + endpoints.getEmployees)
            .then((data) => {
                setEmployees(data);
            })
            .catch((error) => {
                console.error('Error fetching data:', error);
            });


    }, [setUser, endpoints]);

    const handleGenerateData = async () => {
        setLoading(true);
        setModalDesc("Generating ");
        try {
            const data = await generate(endpoints);
            console.log("Returned data -> ", data);
        } catch (error) {
            console.error("Error generating data: ", error);
        } finally {
            setLoading(false);
            window.location.reload();
        }
    };

    const handleMigrateData = async () => {
        setLoading(true);
        setModalDesc("Migrating ");
        setIsClicked(true);
        try {
            const data = await migrate(endpoints);
            console.log("Returned data -> ", data);
        } catch (error) {
            console.error("Error generating data: ", error);
        } finally {
            setLoading(false);
            setEndpoints({
                getMovies: '/movies/nosql',
                getSeats: '/seats/hall/nosql',
                getScreening: '/screening/nosql',
                getReportNedim: '/reports/nedim/nosql',
                getReportMaks: '/reports/first/nosql',
                getActors: '/actors/nosql',
                getDirectors: '/directors/nosql',
                getCustomers: '/customers/nosql',
                getEmployees: '/employees/nosql',
                postTicket: '/tickets/nosql?paymentMethod=',
                postGenerate: '/generate',
                postMigrate: '/migrate'
            });
            navigate("/movies");
        }
    };

    const handleChangeUser = (event) => {
        const selectedUser = event.target.value;
        setUser(selectedUser);
        navigate("/movies");
    };

    const allUsers = [...customers, ...employees];

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

                        <Box sx={{ flexGrow: 0, display: { xs: 'none', md: 'flex' } }}>
                            <Button
                                disabled = {isClicked}
                                key="migrate"
                                onClick={handleMigrateData}
                                sx={{ my: 2, color: 'white', display: 'block', borderRadius: '8px', marginRight: '18px' }}
                            >
                                Migrate Data
                            </Button>
                        </Box>

                        <Box sx={{ flexGrow: 0, display: { xs: 'none', md: 'flex' }, alignItems: 'center' }}>
                            <FormControl variant="standard" sx={{ m: 1, minWidth: 120 }}>
                                <InputLabel id="user-select-label">User</InputLabel>
                                <Select
                                    labelId="user-select-label"
                                    id="user-select"
                                    value={user ? user : ""}
                                    onChange={handleChangeUser}
                                    label="User"
                                >
                                    {allUsers.map((element, index) => (
                                        <MenuItem key={index} value={element}>
                                            {element.firstname} {element.lastname}
                                            {element.roles && element.roles.length > 0 && ` (${element.roles.map(role => role.name).join(', ')})`}
                                        </MenuItem>
                                    ))}
                                </Select>
                            </FormControl>
                        </Box>
                    </Toolbar>
                </Container>
            </AppBar>
            <LoadingModal modalDesc={modalDesc} loading={loading} />
        </>
    );
}

export default MovieplexxAppBar;
