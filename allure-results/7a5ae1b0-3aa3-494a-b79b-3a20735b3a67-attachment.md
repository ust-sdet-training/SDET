# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: booking.spec.ts >> E24 end-to-end bus booking and PNR validation
- Location: assessment-testt\ui--automation\tests\booking.spec.ts:17:5

# Error details

```
Error: expect(locator).not.toBeVisible() failed

Locator:  locator('text=TS-1024-0015')
Expected: not visible
Received: visible
Timeout:  20000ms

Call log:
  - Expect "not toBeVisible" with timeout 20000ms
  - waiting for locator('text=TS-1024-0015')
    43 × locator resolved to <span class="title" data-id="pnr">TS-1024-0015</span>
       - unexpected value "visible"

```

```yaml
- text: TS-1024-0015
```

# Test source

```ts
  1   | import { test, expect } from "./fixtures";
  2   | import { ENV } from "../src/config/env";
  3   | import { LoginPage } from "../src/pages/login.page";
  4   | import { BookingPage } from "../src/pages/booking.page";
  5   | import { PassengerPage } from "../src/pages/passenger.page";
  6   | import { PaymentPage } from "../src/pages/payment.page";
  7   | 
  8   | const empId = "1024";
  9   | const expectedPnrPrefix = `TS-${empId}-`;
  10  | 
  11  | function getFutureDate(daysAhead: number) {
  12  |   const date = new Date();
  13  |   date.setDate(date.getDate() + daysAhead);
  14  |   return date.toISOString().slice(0, 10);
  15  | }
  16  | 
  17  | test("E24 end-to-end bus booking and PNR validation", async ({
  18  |   page,
  19  |   logger,
  20  | }) => {
  21  |   const loginPage = new LoginPage(page);
  22  |   const bookingPage = new BookingPage(page);
  23  |   const passengerPage = new PassengerPage(page);
  24  |   const paymentPage = new PaymentPage(page);
  25  | 
  26  |   await page.goto(ENV.baseUrl);
  27  |   await page.getByRole("link", { name: "Log in" }).click();
  28  |   await loginPage.login(ENV.userEmail, ENV.userPassword);
  29  | 
  30  |   const nextBookingDate = getFutureDate(10);
  31  |   await bookingPage.chooseBusRoute("Go", "Bengaluru BLR", nextBookingDate);
  32  |   await bookingPage.selectBus();
  33  |   await bookingPage.chooseSeat();
  34  |   await bookingPage.continueToPassengerDetails();
  35  | 
  36  |   await passengerPage.fillPassengerDetails();
  37  |   await paymentPage.pay();
  38  |   logger.info("Payment", "Completed", "Completed payment form and clicked pay");
  39  | 
  40  |   await expect(paymentPage.viewTripsButton).toBeVisible({ timeout: 30000 });
  41  |   await paymentPage.viewTripsButton.click();
  42  |   logger.info("Navigation", "Completed", "Reached My Trips view after booking");
  43  | 
  44  |   const pnrText = await page
  45  |     .getByText(expectedPnrPrefix, { exact: false })
  46  |     .first()
  47  |     .textContent();
  48  |   expect(pnrText).toBeTruthy();
  49  |   expect(pnrText?.trim()).toMatch(new RegExp(`^${expectedPnrPrefix}\\d+$`));
  50  |   logger.info("PNR", "Verified", `Found PNR: ${pnrText?.trim()}`);
  51  | 
  52  |   const pnrValues = await page
  53  |     .locator("text=/TS-\\d{4}-\\d+/")
  54  |     .allTextContents();
  55  |   expect(pnrValues.length).toBeGreaterThan(0);
  56  |   pnrValues.forEach((value) =>
  57  |     expect(value.trim()).toMatch(new RegExp(`^${expectedPnrPrefix}\\d+$`)),
  58  |   );
  59  |   logger.info(
  60  |     "My Trips",
  61  |     "Ownership",
  62  |     `Verified ${pnrValues.length} booking(s) belong to ${expectedPnrPrefix}`,
  63  |   );
  64  | 
  65  |   // Cancel the first booking so the test can be re-run cleanly
  66  |   const cancelButton = page.getByRole("button", { name: "Cancel" }).first();
  67  |   await cancelButton.click();
  68  | 
  69  |   // Try common confirmation buttons in the cancellation modal
  70  |   const confirmButtons = [
  71  |     page.getByRole("button", { name: "Confirm" }),
  72  |     page.getByRole("button", { name: "Yes" }),
  73  |     page.getByRole("button", { name: /Yes, cancel/i }),
  74  |     page.getByRole("button", {
  75  |       name: /Confirm Cancellation|Cancel Booking|Confirm Cancel/i,
  76  |     }),
  77  |   ];
  78  | 
  79  |   let cancelled = false;
  80  |   for (const btn of confirmButtons) {
  81  |     try {
  82  |       if (await btn.isVisible()) {
  83  |         await btn.click();
  84  |         cancelled = true;
  85  |         break;
  86  |       }
  87  |     } catch (e) {
  88  |       // ignore and try next
  89  |     }
  90  |   }
  91  | 
  92  |   if (!cancelled) {
  93  |     page.on("dialog", (d) => d.accept());
  94  |   }
  95  | 
  96  |   // Wait for the PNR to disappear which indicates cancellation succeeded
  97  |   if (pnrText) {
> 98  |     await expect(page.locator(`text=${pnrText.trim()}`)).not.toBeVisible({
      |                                                              ^ Error: expect(locator).not.toBeVisible() failed
  99  |       timeout: 20000,
  100 |     });
  101 |     logger.info(
  102 |       "Cancellation",
  103 |       "Completed",
  104 |       `Cancelled booking ${pnrText.trim()}`,
  105 |     );
  106 |   }
  107 | 
  108 |   await test.info().attach("booking-diagnosis", {
  109 |     body: Buffer.from(logger.getTable(), "utf-8"),
  110 |     contentType: "text/markdown",
  111 |   });
  112 | });
  113 | 
```