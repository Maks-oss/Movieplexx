import {BrowserRouter as Router, Routes, Route, Outlet} from "react-router-dom";
import MoviesListPage from "./pages/MoviesListPage";
import MovieplexxAppBar from "./components/MovieplexxAppBar";
import MovieScreeningsPage from "./pages/MovieScreeningsPage";
import SeatPickerPage from "./pages/SeatPickerPage";
import TicketPage from "./pages/TicketPage";
import AddMoviePage from "./pages/AddMoviePage";
function App() {
    return (
        <Router>
            <MovieplexxAppBar/>
            <Routes>
                <Route path="/" element={<Outlet/>}>
                    <Route index element={<p> Home page</p>}/>
                    <Route path="/movies" element={<MoviesListPage/>}/>
                    <Route path="/movies/screening" element={<MovieScreeningsPage/>} />
                    <Route path="/movies/screening/seatPicker" element={<SeatPickerPage/>} />
                    <Route path="/movies/screening/seatPicker/ticket" element={<TicketPage/>} />
                    <Route path="/newmovie" element={<AddMoviePage/>} />
                    <Route path="*" element={<p>Not found</p>}/>
                </Route>
            </Routes>
        </Router>
    );
}
export default App;
