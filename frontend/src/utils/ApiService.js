import {useMovieplexxContext} from "./MovieplexxContext";
import {authenticate} from "./ApiCalls";

class ApiService {
    movieplexxContext = useMovieplexxContext()

    async fetchApi(endpoint, isProtected = false) {
        console.log("Calling: ", endpoint)
        const response = isProtected ? await fetch(endpoint, {
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${this.movieplexxContext.accessToken}`
            }
        }) : await fetch(endpoint);
        // if (response.status === 401) {
        //     console.log("Not authorized user")
        //     return null
        // }
        const data = await response.json();
        console.log(data);
        return data
    }
    async createTicketRequest(paymentMethod , data={}) {
        console.log('BEFORE SEND: ' + JSON.stringify(data))
        const response = await fetch(`http://localhost:5433/tickets?paymentMethod=${paymentMethod}`, {
            method: "POST", // *GET, POST, PUT, DELETE, etc.
            mode: "cors", // no-cors, *cors, same-origin
            cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
            credentials: "same-origin", // include, *same-origin, omit
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${this.movieplexxContext.accessToken}`
            },
            redirect: "follow", // manual, *follow, error
            referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
            body: JSON.stringify(data), // body data type must match "Content-Type" header
        });
        let json = response.json();
        console.log(json)
        return json;
    }
    async addMovie( data={}, token) {
        console.log('BEFORE SEND: ' + JSON.stringify(data))
        return await fetch(`http://localhost:5433/movies`, {
            method: "POST", // *GET, POST, PUT, DELETE, etc.
            mode: "cors", // no-cors, *cors, same-origin
            cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
            credentials: "same-origin", // include, *same-origin, omit
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${this.movieplexxContext.accessToken}`
            },
            redirect: "follow", // manual, *follow, error
            referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
            body: JSON.stringify(data), // body data type must match "Content-Type" header
        });
    }
    async authenticate(body) {
        return await fetch(`http://localhost:5433/login`, {
            method: "POST", // *GET, POST, PUT, DELETE, etc.
            mode: "cors", // no-cors, *cors, same-origin
            cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
            credentials: "same-origin", // include, *same-origin, omit
            headers: {
                "Content-Type": "application/json",
            },
            redirect: "follow", // manual, *follow, error
            referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
            body: JSON.stringify(body), // body data type must match "Content-Type" header
        });
    }
    async generate() {
        return await fetch(`http://localhost:5433/generate`, {
            method: "POST", // *GET, POST, PUT, DELETE, etc.
            mode: "cors", // no-cors, *cors, same-origin
            cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
            credentials: "same-origin", // include, *same-origin, omit
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${this.movieplexxContext.accessToken}`
            },
            redirect: "follow", // manual, *follow, error
            referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        });
    }
}

export default function useApiService() {
    return new ApiService();
}
