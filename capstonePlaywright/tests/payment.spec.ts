import { test } from "@playwright/test";

import { LoginPage } from "../pages/LoginPage";
import { SearchPage } from "../pages/SearchPage";
import { PaymentPage } from "../pages/payment";

import { user } from "../fixtures/test-fixtures";

test("Payment Page", async ({ page }) => {

    const login = new LoginPage(page);
    const search = new SearchPage(page);
    const payment = new PaymentPage(page);

    // Login
    await login.goto();

    await login.login(
        user.username,
        user.password
    );

    // Search Bus
    await search.search();

    // Complete Booking + Payment
    await payment.payment();

});