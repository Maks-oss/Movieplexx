import React, { createContext, useState, useContext } from 'react';

const MovieplexxContext = createContext();

export const MovieplexxProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [endpoints, setEndpoints] = useState({
        getMovies: '/movies/sql',
        getSeats: '/seats/hall/sql',
        getScreening: '/screening/sql',
        getReportNedim: '/reports/nedim/sql',
        getReportMaks: '/reports/first/sql',
        getActors: '/actors/sql',
        getDirectors: '/directors/sql',
        getCustomers: '/customers/sql',
        getEmployees: '/employees/sql',
        postTicket: '/tickets/sql?paymentMethod=',
        postGenerate: '/generate',
        postMigrate: '/migrate'
    });

    return (
        <MovieplexxContext.Provider value={{ user, setUser, endpoints, setEndpoints }}>
            {children}
        </MovieplexxContext.Provider>
    );
};

export const useMovieplexxContext = () => {
    return useContext(MovieplexxContext);
};
