import http from "k6/http";
import { check } from "k6";
import { config } from "../config/config.js";

export default function () {

    const response = http.get(
        `${config.baseUrl}/api/bookings/123`
    );

    check(response, {
        "BOLA Protected": (r) => r.status !== 200
    });
}