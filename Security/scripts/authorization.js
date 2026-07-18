import http from "k6/http";
import { check } from "k6";
import { config } from "../config/config.js";

export default function () {

    const response = http.get(
        `${config.baseUrl}/api/admin/users`
    );

    check(response, {
        "Forbidden (403)": (r) => r.status === 403
    });
}