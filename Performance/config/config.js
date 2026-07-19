import { check } from "k6";

export const BASE_URL = __ENV.BASE_URL;
export const EMAIL = __ENV.EMAIL;
export const PASSWORD = __ENV.PASSWORD;

export const options = {
    vus: 20,
    duration: "30s",

    thresholds: {
        http_req_duration: ["p(95)<2000"],
        http_req_failed: ["rate<0.01"]
    }
};

export const jsonHeaders = {
    headers: {
        "Content-Type": "application/json"
    }
};

export function authHeaders(token) {
    return {
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        }
    };
}

export function validate(response, expectedStatus) {

    check(response, {
        "Status Code": (r) => r.status === expectedStatus,
        "Response Time < 2 sec": (r) => r.timings.duration < 2000
    });

}