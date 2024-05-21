
export async function fetchApi(endpoint) {
    const response = await fetch(endpoint);
    const data = await response.json();
    console.log(data);
    return data
}
//http://localhost:5433/ticket
export async function postRequest(endpoint, data={}) {
    console.log('BEFORE SEND: ' + JSON.stringify(data))
    const response = await fetch(endpoint, {
        method: "POST", // *GET, POST, PUT, DELETE, etc.
        mode: "cors", // no-cors, *cors, same-origin
        cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "same-origin", // include, *same-origin, omit
        headers: {
            "Content-Type": "application/json",
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: "follow", // manual, *follow, error
        referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        body: JSON.stringify(data), // body data type must match "Content-Type" header
    });
    let json = response.json();
    console.log(json)
    return json;
}