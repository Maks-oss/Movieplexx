import React, { createContext, useContext, useMemo, useState } from 'react';
import {useLocalStorage} from "./LocalStorage";
import {useNavigate} from "react-router-dom";
import useApiService from "./ApiService";

const MovieplexxContext = createContext();

export const MovieplexxProvider = ({ children }) => {
    const [user, setUser] = useLocalStorage("user",null);
    const [accessToken, setAccessToken] = useLocalStorage("token",null);
    const [isTokenValid, setIsValidToken] = useLocalStorage("tokenValidity",false);
    const navigation = useNavigate();

    const logout = () => {
        setAccessToken(null)
        navigation("/", { replace: true });
    };

    const value = useMemo(
        () => ({
            accessToken,
            user,
            setUser,
            isTokenValid,
            setIsValidToken,
            setAccessToken,
            logout,
        }),
        [accessToken, isTokenValid]
    );
    return (
        <MovieplexxContext.Provider value={value}>
            {children}
        </MovieplexxContext.Provider>
    );
};

export const useMovieplexxContext = () => {
    return useContext(MovieplexxContext);
};

