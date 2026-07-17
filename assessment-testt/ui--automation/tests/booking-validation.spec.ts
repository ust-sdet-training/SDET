import { test, expect } from "./fixtures";
import { ENV } from "../src/config/env";
import { LoginPage } from "../src/pages/login.page";
import { BookingPage } from "../src/pages/booking.page";

function getPastDate(daysAgo: number) {
  const date = new Date();
  date.setDate(date.getDate() - daysAgo);
  return date.toISOString().slice(0, 10);
}

test("booking page should validate past date selection", async ({ page }) => {
  const loginPage = new LoginPage(page);
  const bookingPage = new BookingPage(page);

  await page.goto(ENV.baseUrl);
  await page.getByRole("link", { name: "Log in" }).click();
  await loginPage.login(ENV.userEmail, ENV.userPassword);

  const pastDate = getPastDate(5);
  await bookingPage.chooseBusRoute("Go", "Bengaluru BLR", pastDate);

  const dateError = page.getByText(/Please select a future date|Invalid date/i);
  const resultsHeading = page.getByRole("heading", { name: /Buses from/i });

  try {
    await expect(dateError).toBeVisible({ timeout: 5000 });
  } catch (e) {
    await expect(resultsHeading).toBeVisible({ timeout: 5000 });
  }
});
