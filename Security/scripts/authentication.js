import http from "k6/http";
import { check } from "k6";
import { config } from "../config/config.js";

export default function () {

    const response = http.get(
        `${config.baseUrl}/api/bookings`
    );

    check(response, {
        "Unauthorized (401)": (r) => r.status === 401
    });
}