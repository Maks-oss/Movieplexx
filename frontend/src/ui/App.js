import {BrowserRouter as Router, Outlet, Route, Routes} from "react-router-dom";
import MoviesListPage from "./pages/MoviesListPage";
import MovieScreeningsPage from "./pages/MovieScreeningsPage";
import SeatPickerPage from "./pages/SeatPickerPage";
import TicketPage from "./pages/TicketPage";
import AddMoviePage from "./pages/AddMoviePage";
import FirstReportPage from "./pages/FirstReportPage";
import SecondReportPage from "./pages/SecondReportPage";
import MovieplexxAppBar from "./components/MovieplexxAppBar";
import SignInPage from "./pages/SignInPage";
import ProtectedRoute from "./components/auth/ProtectedRoute";
import {MovieplexxProvider} from "../utils/MovieplexxContext";

function App() {
    return (
        <Router>
            <MovieplexxProvider>
                <Routes>
                    <Route path="/login" element={<SignInPage/>}/>
                    <Route path="/" element={
                        <>
                            <ProtectedRoute>
                                <MovieplexxAppBar/>
                                <Outlet/>
                            </ProtectedRoute>
                        </>
                    }>
                        <Route path="/movies" element={<ProtectedRoute><MoviesListPage/></ProtectedRoute>}/>
                        <Route path="/movies/:movieid"
                               element={<ProtectedRoute><MovieScreeningsPage/></ProtectedRoute>}/>
                        <Route path="/movies/:movieid/:hallid"
                               element={<ProtectedRoute><SeatPickerPage/></ProtectedRoute>}/>
                        <Route path="/movies/:movieid/:hallid/:ticket"
                               element={<ProtectedRoute><TicketPage/></ProtectedRoute>}/>
                        <Route path="/newmovie" element={<ProtectedRoute><AddMoviePage/></ProtectedRoute>}/>
                        <Route path="/firstreport" element={<ProtectedRoute><FirstReportPage/></ProtectedRoute>}/>
                        <Route path="/secondreport" element={<ProtectedRoute><SecondReportPage/></ProtectedRoute>}/>
                        <Route path="*" element={<p>Not found</p>}/>
                    </Route>
                </Routes>
            </MovieplexxProvider>
        </Router>
    );
}

export default App;
