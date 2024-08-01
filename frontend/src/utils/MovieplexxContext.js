import React, { createContext, useContext, useMemo, useState } from 'react';
import {useLocalStorage} from "./LocalStorage";
import {useNavigate} from "react-router-dom";
import useApiService from "./ApiService";

const UserContext = createContext();
const NavigationContext = createContext()
export const UserProvider = ({ children }) => {
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
        <UserContext.Provider value={value}>
            {children}
        </UserContext.Provider>
    );
};
export const NavigationParamsProvider = ({children}) => {
    const [params, setParams] = useLocalStorage("params", {})
    const value = useMemo(
        () => ({
            params, setParams
        }),
        [params]
    );
    return (
        <NavigationContext.Provider value={value}>
            {children}
        </NavigationContext.Provider>
    );
}
export const useUserContext = () => {
    return useContext(UserContext);
};
export const useNavigationParamsContext = () => {
    return useContext(NavigationContext);
};
