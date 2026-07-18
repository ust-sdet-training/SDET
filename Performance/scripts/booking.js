import http from "k6/http";
import { check } from "k6";
import { config } from "../config/config.js";
import { users } from "../data/users.js";
import { headers } from "../utils/helpers.js";

export const options = {
    vus: config.users,
    duration: config.duration,
};

export default function () {

    // Login
    const loginResponse = http.post(
        `${config.baseUrl}/api/auth/login`,
        JSON.stringify({
            username: users.standard.username,
            password: users.standard.password
        }),
        {
            headers: {
                "Content-Type": "application/json"
            }
        }
    );

    const token = loginResponse.json("token");

    // Search
    const searchResponse = http.get(
        `${config.baseUrl}/api/search/buses?from=LKO&to=BLR&date=2026-08-12`,
        headers(token)
    );

    check(searchResponse, {
        "Search Success": (r) => r.status === 200,
    });

    const inventoryId = searchResponse.json("buses[0].id");

    // Hold Booking
    const holdResponse = http.post(
        `${config.baseUrl}/api/bookings`,
        JSON.stringify({
            journeyType: "bus",
            inventoryId: inventoryId,
            seatIds: ["L1"],
            autoAssign: true,
            fare: 300
        }),
        headers(token)
    );

    check(holdResponse, {
        "Booking Hold Success": (r) => r.status === 201,
    });
}