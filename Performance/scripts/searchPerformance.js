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


    const response = http.get(

        `${BASE_URL}/api/flights?from=BOM&to=MAA&date=2026-07-30`,

        authHeaders(token)

    );

    validate(response, 200);

    sleep(1);
}