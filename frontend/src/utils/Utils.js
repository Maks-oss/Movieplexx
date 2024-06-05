export function groupSeatsByRow(seats) {
    return seats.reduce((acc, seat) => {
        const row = seat.row;
        if (!acc[row]) {
            acc[row] = {
                rowSeats: []
            };
        }
        acc[row].rowSeats.push({
            id: seat.id,
            number: seat.number,
            type: seat.type,
            occupied: seat.ticket !== null
        });
        // console.log('seats: ' + JSON.stringify(Object.entries(acc).slice(0,3)))
        return acc;
    }, {});
}


export function groupMovieScreeningsByCinema(movieScreenings) {
    return movieScreenings.reduce((acc, screening) => {
        const cinemaId = screening.moviehall.cinema.id;
        if (!acc[cinemaId]) {
            acc[cinemaId] = {
                cinema: screening.moviehall.cinema,
                screenings: []
            };
        }
        acc[cinemaId].screenings.push({
            id: screening.id,
            moviehall: screening.moviehall,
            startTime: screening.startTime,
            endTime: screening.endTime,
            movie: screening.movie
        });
        return acc;
    }, {});
}
export function getTime(timestamp) {
    const date = new Date(timestamp);
    let utcHours = date.getUTCHours();
    let utcMinutes = date.getUTCMinutes();
    return (utcHours < 10 ? `0${utcHours}` : utcHours)
        + ':' +
        (utcMinutes < 10 ? `0${utcMinutes}` : utcMinutes);
}
