import React from 'react';
import { Navigate } from 'react-router-dom';
import {useMovieplexxContext} from "../../utils/MovieplexxContext";

export const ProtectedRoute = ({ children }) => {
    const { accessToken, isTokenValid } = useMovieplexxContext();
    if (!accessToken || !isTokenValid) {
        // user is not authenticated
        return <Navigate to="/login"/>;
    }
    return children;
};

export default ProtectedRoute;
