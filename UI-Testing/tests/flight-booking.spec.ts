import { expect } from "@playwright/test";

import { test } from "../fixtures/evidence.fixture";

import { FlightBookingFlow } from "../flows/FlightBookingFlow";

test.describe("Flight Booking", () => {

    test("User should successfully book a flight", async ({ page, log }) => {
    const bookingFlow = new FlightBookingFlow(page, log);
    await bookingFlow.bookFlight();

        }
    );

});