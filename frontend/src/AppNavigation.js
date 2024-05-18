import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import {BrowserRouter as Router, Routes, Route, Link, Outlet} from "react-router-dom";
import MoviesListPage from "./pages/MoviesListPage";

function AppNavigation() {
    return (
        <Router>
            <CustomNavBar/>
            <Routes>
                <Route path="/" element={<Outlet/>}>
                    <Route index element={<p> Home page</p>}/>
                    <Route path="/movies" element={<MoviesListPage/>}/>
                    <Route path="*" element={<p>Not found</p>}/>
                </Route>
            </Routes>
        </Router>
    );
}
function CustomNavBar() {
    return (
        <Navbar className="bg-body-tertiary" bg="dark" data-bs-theme="dark">
            <Container>
                <Navbar.Brand as={Link} to="/">Movieplexx</Navbar.Brand>
                <Nav className="me-auto">
                    <Nav.Link as={Link} to="/movies">Movies</Nav.Link>
                </Nav>
            </Container>
        </Navbar>
    )
}
export default AppNavigation;
