import http from "k6/http";
import { check } from "k6";

export const options = {
    vus: 10,
    iterations: 100,

    thresholds: {
        http_req_duration: ["p(95)<1000"],
        http_req_failed: ["rate<0.01"]
    }
};

export function setup() {

    const loginPayload = JSON.stringify({
        email: __ENV.USER_EMAIL,
        password: __ENV.PASSWORD
    });

    const loginResponse = http.post(
        `${__ENV.BASE_URL}/api/auth/login`,
        loginPayload,
        {
            headers: {
                "Content-Type": "application/json"
            }
        }
    );

    check(loginResponse, {
        "Login Successful": (r) => r.status === 200
    });

    const token = loginResponse.json("token");

    return { token };
}

export default function (data) {

    const headers = {
        Authorization: `Bearer ${data.token}`
    };

    const url =
        `${__ENV.BASE_URL}/api/flights` +
        `?from=PUN` +
        `&to=DEL` +
        `&date=2026-08-10` +
        `&pax=1` +
        `&class=economy`;

    const response = http.get(url, { headers });

    console.log(`Status: ${response.status}`);

    check(response, {
        "Status 200": (r) => r.status === 200,
        "Flights Returned": (r) => r.json("count") > 0
    });
}