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

  // Cancel the first booking so the test can be re-run cleanly
  const cancelButton = page.getByRole("button", { name: "Cancel" }).first();
  await cancelButton.click();

  // Try common confirmation buttons in the cancellation modal
  const confirmButtons = [
    page.getByRole("button", { name: "Confirm" }),
    page.getByRole("button", { name: "Yes" }),
    page.getByRole("button", { name: /Yes, cancel/i }),
    page.getByRole("button", {
      name: /Confirm Cancellation|Cancel Booking|Confirm Cancel/i,
    }),
  ];

  let cancelled = false;
  for (const btn of confirmButtons) {
    try {
      if (await btn.isVisible()) {
        await btn.click();
        cancelled = true;
        break;
      }
    } catch (e) {
      // ignore and try next
    }
  }

  if (!cancelled) {
    page.on("dialog", (d) => d.accept());
  }

  if (pnrText) {
    const pnrTrim = pnrText.trim();
    const pnrLocator = page.locator(`text=${pnrTrim}`).first();
    try {
      await expect(pnrLocator).not.toBeVisible({ timeout: 10000 });
      logger.info("Cancellation", "Completed", `Cancelled booking ${pnrTrim}`);
    } catch (e) {
      const bookingCard = page.locator(`:has-text("${pnrTrim}")`).first();
      const refundedBadge = bookingCard
        .getByText("REFUNDED", { exact: false })
        .first();
      await expect(refundedBadge).toBeVisible({ timeout: 10000 });
      logger.info(
        "Cancellation",
        "Completed",
        `Booking ${pnrTrim} marked REFUNDED`,
      );
    }
  }

  await test.info().attach("booking-diagnosis", {
    body: Buffer.from(logger.getTable(), "utf-8"),
    contentType: "text/markdown",
  });
});
