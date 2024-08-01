import {useUserContext} from "./MovieplexxContext";

class ApiService {
    movieplexxContext = useUserContext()

    async fetchApi(endpoint, isProtected = false) {
        console.log("Calling: ", endpoint)
        try {
            const response = isProtected ? await fetch(endpoint, {
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${this.movieplexxContext.accessToken}`
                }
            }) : await fetch(endpoint);
            if (isProtected) {
                this.#checkResponse(response)
            }
            // const data = await response.json();
            // console.log(data);
            return await response.json()
        } catch (error) {
            console.error("Failed to fetch api:", error)
            return null
        }
    }

    async createTicketRequest(paymentMethod, data = {}) {
        console.log('BEFORE SEND: ' + JSON.stringify(data))
        try {
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
            this.#checkResponse(response)
            let json = response.json();
            console.log(json)
            return json;
        } catch (error) {
            console.error("Failed to create ticket:", error)
            return null
        }
    }

    async addMovie(data = {}, token) {
        console.log('BEFORE SEND: ' + JSON.stringify(data))
        const response = await fetch(`http://localhost:5433/movies`, {
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
        this.#checkResponse(response)
        return response;
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
        try {
            let response = await fetch(`http://localhost:5433/generate`, {
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
            this.#checkResponse(response)
            return {message: "generated"}
        } catch (error) {
            return {message: error}
        }
    }

    #checkResponse(response) {
        if (response.status === 401) {
            this.movieplexxContext.setIsValidToken(false)
            throw "Invalid access token"
        }
    }
}

export default function useApiService() {
    return new ApiService();
}
