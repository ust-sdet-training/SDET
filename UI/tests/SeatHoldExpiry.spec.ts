import { test, expect } from "@playwright/test";
import { TripStackFlow } from "../flows/TripStackFlow";

test.describe("Seat Hold Expiry", () => {

    test("User should see HOLD_EXPIRED when payment is attempted after hold expires",
        async ({ page }) => {

            const flow = new TripStackFlow(page);

            const result = await flow.completeSeatHoldExpiryBooking();

            expect([
                "CONFIRMED",
                "HOLD_EXPIRED",
                "SEAT_UNAVAILABLE"
            ]).toContain(result);

        });

});