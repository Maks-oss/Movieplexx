import React from 'react';
import { Navigate } from 'react-router-dom';
import {useUserContext} from "../../utils/MovieplexxContext";
import { useLocation } from 'react-router-dom';

export const ProtectedRoute = ({ children }) => {
    const { accessToken, isTokenValid } = useUserContext();
    const location = useLocation()
    if (!accessToken || !isTokenValid) {
        // user is not authenticated
        return <Navigate to="/login" state={{from: location.pathname}}/>;
    }
    return children;
};

export default ProtectedRoute;
