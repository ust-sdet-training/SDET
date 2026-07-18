import http from "k6/http";
import { check } from "k6";
import { config } from "../config/config.js";

export default function () {

    const response = http.get(config.baseUrl);

    check(response, {
        "Has Content-Security-Policy": (r) =>
            r.headers["Content-Security-Policy"] !== undefined,

        "Has X-Content-Type-Options": (r) =>
            r.headers["X-Content-Type-Options"] !== undefined
    });
}