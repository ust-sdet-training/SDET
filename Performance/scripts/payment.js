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

    const bookingId = holdResponse.json("id");

    // Payment
    const paymentResponse = http.post(
        `${config.baseUrl}/api/payments/${bookingId}`,
        JSON.stringify({
            method: "CARD"
        }),
        headers(token)
    );

    check(paymentResponse, {
        "Payment Success": (r) => r.status === 200,
    });

    // Confirm Booking
    const confirmResponse = http.post(
        `${config.baseUrl}/api/bookings/${bookingId}/confirm`,
        null,
        headers(token)
    );

    check(confirmResponse, {
        "Booking Confirmed": (r) => r.status === 200,
    });
}