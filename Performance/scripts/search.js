import http from "k6/http";
import { check } from "k6";
import { config } from "../config/config.js";

export const options = {
    vus: config.users,
    duration: config.duration,
};

export default function () {

    const response = http.get(
        `${config.baseUrl}/api/search/buses?from=LKO&to=BLR&date=2026-08-12`
    );

    check(response, {
        "Status is 200": (r) => r.status === 200,
    });
}