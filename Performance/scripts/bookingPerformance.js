import http from "k6/http";
import { sleep } from "k6";

import {
    BASE_URL,
    EMAIL,
    PASSWORD,
    options,
    jsonHeaders,
    authHeaders,
    validate
} from "../config/config.js";

export { options };

export default function () {


    const loginResponse = http.post(

        `${BASE_URL}/auth/login`,

        JSON.stringify({
            email: EMAIL,
            password: PASSWORD
        }),

        jsonHeaders

    );

    validate(loginResponse, 200);

    const token = loginResponse.json("token");



    const searchResponse = http.get(

        `${BASE_URL}/api/flights?from=BOM&to=MAA&date=2026-07-30`,

        authHeaders(token)

    );

    validate(searchResponse, 200);

    const flightId = searchResponse.json("flights[0].id");


    const bookingPayload = JSON.stringify({

        flightId: flightId,

        passengers: [
            {
                firstName: "Salman",
                lastName: "Benny",
                age: 24
            }
        ]

    });

    const bookingResponse = http.post(

        `${BASE_URL}/bookings`,

        bookingPayload,

        authHeaders(token)

    );

    validate(bookingResponse, 201);

    sleep(1);
}