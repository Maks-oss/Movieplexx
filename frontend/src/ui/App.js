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
import ProtectedRoute from "./navigation/ProtectedRoute";
import {NavigationParamsProvider, UserProvider} from "../utils/MovieplexxContext";

function App() {
    return (
        <Router>
            <UserProvider>
                <NavigationParamsProvider>
                    <Routes>
                        <Route path="/login" element={<SignInPage/>}/>
                        <Route path="/" element={
                            <>
                                <MovieplexxAppBar/>
                                <Outlet/>
                            </>
                        }>
                            <Route path="/movies" element={<MoviesListPage/>}/>
                            <Route path="/movies/:movieid"
                                   element={<MovieScreeningsPage/>}/>
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
                </NavigationParamsProvider>
            </UserProvider>
        </Router>
    );
}

export default App;
