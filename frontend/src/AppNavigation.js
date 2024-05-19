import {BrowserRouter as Router, Routes, Route, Outlet} from "react-router-dom";
import MoviesListPage from "./pages/MoviesListPage";
import MovieplexxAppBar from "./components/MovieplexxAppBar";
function AppNavigation() {
    return (
        <Router>
            <MovieplexxAppBar/>
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
export default AppNavigation;
