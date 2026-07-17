import { test } from "@playwright/test";

import { LoginPage } from "../pages/LoginPage";
import { SearchPage } from "../pages/SearchPage";
import { BookingPage } from "../pages/SeatsPassengerDetails";

import { user } from "../fixtures/test-fixtures";

test("Booking Seat", async ({ page }) => {

    const login = new LoginPage(page);
    const search = new SearchPage(page);
    const booking = new BookingPage(page);

    await login.goto();

    await login.login(
        user.username,
        user.password
    );

    await search.search();

    await booking.selectBus();

    await booking.selectSeat();

    await booking.fillPassengerDetails(
        "Peggy",
        "P",
        "22",
        user.username,
        "9087789065"
    );

    await booking.continueToPayment();

});