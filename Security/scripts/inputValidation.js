import http from "k6/http";
import { check } from "k6";
import { config } from "../config/config.js";

export default function () {

    const response = http.get(
        `${config.baseUrl}/api/search?from=<script>alert(1)</script>`
    );

    check(response, {
        "Input Validation": (r) => r.status !== 500
    });
}