import { test, expect } from "@playwright/test";

import { LoginPage } from "../pages/LoginPage";
import { HomePage } from "../pages/HomePage";
import { FlightResultPage } from "../pages/FlightResultPage";
import { SeatPage } from "../pages/SeatPage";
import { BookingData } from "../test-data/BookingData";

test.describe("Negative Seat Selection", () => {

    test("User should not continue without selecting a seat", async ({ page }) => {

        const login = new LoginPage(page);
        const home = new HomePage(page);
        const result = new FlightResultPage(page);
        const seat = new SeatPage(page);

        await login.navigateToLogin();

        await login.login(
            BookingData.EMAIL,
            BookingData.PASSWORD
        );

        await home.searchFlight(
            BookingData.FIRST_SOURCE,
            BookingData.FIRST_DESTINATION,
            BookingData.FIRST_TRAVEL_AFTER_DAYS
        );

        await result.chooseFirstFlight();

        await seat.verifySeatPageLoaded();

        // Do NOT select any seat

        await expect(
            seat.locator.continueButton
        ).toBeDisabled();

        await expect(
            seat.locator.selectedSeatLabel
        ).toContainText("none");

    });

});