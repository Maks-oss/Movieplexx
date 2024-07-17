import React, { createContext, useContext, useMemo, useState } from 'react';
import {useLocalStorage} from "./LocalStorage";
import {useNavigate} from "react-router-dom";
import useApiService from "./ApiService";

const MovieplexxContext = createContext();

export const MovieplexxProvider = ({ children }) => {
    const [user, setUser] = useLocalStorage("user",null);
    const [accessToken, setAccessToken] = useState(null);
    const apiService = useApiService()
    // const [accessToken, setAccessToken] = useLocalStorage("token",null);
    const navigation = useNavigate();
    // const login = async (data) => {
    //     apiService.authenticate(data).then(token => {
    //         if (!token.ok) {
    //             token.json().then(err => {
    //                 console.log(err)
    //             })
    //         }
    //         setAccessToken(token.accessToken)
    //         navigation("/movies");
    //     }).catch(error => {
    //         console.log(error)
    //     })
    //
    // };

    // call this function to sign out logged in user
    const logout = () => {
        setAccessToken(null)
        navigation("/", { replace: true });
    };

    const value = useMemo(
        () => ({
            accessToken,
            user,
            setUser,
            // login,
            logout,
        }),
        [accessToken]
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

