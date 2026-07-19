import http from "k6/http";
import { sleep } from "k6";

import {
    BASE_URL,
    EMAIL,
    PASSWORD,
    options,
    jsonHeaders,
    validate
} from "../config/config.js";

export { options };

export default function () {

    const payload = JSON.stringify({
        email: EMAIL,
        password: PASSWORD
    });

    const response = http.post(
        `${BASE_URL}/auth/login`,
        payload,
        jsonHeaders
    );

    validate(response, 200);

    sleep(1);
}