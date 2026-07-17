import { test, expect } from "./fixtures";
import { ENV } from "../src/config/env";
import { LoginPage } from "../src/pages/login.page";
import { BookingPage } from "../src/pages/booking.page";
import { PassengerPage } from "../src/pages/passenger.page";
import { PaymentPage } from "../src/pages/payment.page";

const empId = "1024";
const expectedPnrPrefix = `TS-${empId}-`;

function getFutureDate(daysAhead: number) {
  const date = new Date();
  date.setDate(date.getDate() + daysAhead);
  return date.toISOString().slice(0, 10);
}

test("E24 end-to-end bus booking and PNR validation", async ({
  page,
  logger,
}) => {
  const loginPage = new LoginPage(page);
  const bookingPage = new BookingPage(page);
  const passengerPage = new PassengerPage(page);
  const paymentPage = new PaymentPage(page);

  await page.goto(ENV.baseUrl);
  await page.getByRole("link", { name: "Log in" }).click();
  await loginPage.login(ENV.userEmail, ENV.userPassword);

  const nextBookingDate = getFutureDate(10);
  await bookingPage.chooseBusRoute("Go", "Bengaluru BLR", nextBookingDate);
  await bookingPage.selectBus();
  await bookingPage.chooseSeat();
  await bookingPage.continueToPassengerDetails();

  await passengerPage.fillPassengerDetails();
  await paymentPage.pay();
  logger.info("Payment", "Completed", "Completed payment form and clicked pay");

  await expect(paymentPage.viewTripsButton).toBeVisible({ timeout: 30000 });
  await paymentPage.viewTripsButton.click();
  logger.info("Navigation", "Completed", "Reached My Trips view after booking");

  const pnrText = await page
    .getByText(expectedPnrPrefix, { exact: false })
    .first()
    .textContent();
  expect(pnrText).toBeTruthy();
  expect(pnrText?.trim()).toMatch(new RegExp(`^${expectedPnrPrefix}\\d+$`));
  logger.info("PNR", "Verified", `Found PNR: ${pnrText?.trim()}`);

  const pnrValues = await page
    .locator("text=/TS-\\d{4}-\\d+/")
    .allTextContents();
  expect(pnrValues.length).toBeGreaterThan(0);
  pnrValues.forEach((value) =>
    expect(value.trim()).toMatch(new RegExp(`^${expectedPnrPrefix}\\d+$`)),
  );
  logger.info(
    "My Trips",
    "Ownership",
    `Verified ${pnrValues.length} booking(s) belong to ${expectedPnrPrefix}`,
  );

  await test.info().attach("booking-diagnosis", {
    body: Buffer.from(logger.getTable(), "utf-8"),
    contentType: "text/markdown",
  });
});
