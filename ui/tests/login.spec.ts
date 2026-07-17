import { test, expect } from '@playwright/test';

test.describe('TripStack Login', () => {

    test('Login with valid credentials', async ({ page }) => {

        // Open Application
        await page.goto('/');

        // Open Login Page
        await page.locator('a[href="/login"]').click();

        // Verify Login Page
        await expect(page).toHaveURL(/login/);

        // Enter Email
        await page.locator('[data-id="login-email"]')
            .fill(process.env.EMAIL!);

        // Enter Password
        await page.locator('[data-id="login-password"]')
            .fill(process.env.PASSWORD!);

        // Click Sign In
        await page.locator('[data-id="login-submit"]').click();

        // Verify Successful Login
        await expect(page).not.toHaveURL(/login/);

        // -------------------------------
// Search Flight
// -------------------------------

// From
await page.locator('#home-from').fill('COK');
await page.getByRole('option').first().click();

// To
await page.locator('#home-to').fill('BOM');
await page.getByRole('option').first().click();

// Date (+22 days)
const travelDate = new Date();
travelDate.setDate(travelDate.getDate() + 22);

const formattedDate = travelDate.toISOString().split('T')[0];

await page.locator('#home-date').fill(formattedDate);

// Search
await page.locator('button[type="submit"]').click();

    
    // Verify at least one flight is available
await expect(
    page.locator('#results article').first()
).toBeVisible();

// Click Book for the first available flight
await page
    .locator('#results article')
    .first()
    .locator('a[role="button"]')
    .click();

 // -------------------------------
// Seat Selection
// -------------------------------

// Verify Seat Map
await expect(page.locator('[data-layout="cabin"]')).toBeVisible();

// Get all available seats
const availableSeats = page.locator('.seat.available');

// Ensure at least one seat is available
const seatCount = await availableSeats.count();
expect(seatCount).toBeGreaterThan(0);

// Select a random available seat
const randomIndex = Math.floor(Math.random() * seatCount);

console.log(`Total Available Seats : ${seatCount}`);
console.log(`Selecting Seat Index : ${randomIndex}`);

await availableSeats.nth(randomIndex).click();

// Continue
await page.locator('#continue-btn').click();




// Verify Passenger Page
await expect(page).toHaveURL(/book\/passenger/);

// Passenger Details
// Passenger Details (Dynamic Seat)

await page.locator('input[name^="firstName_"]').fill('Olivia');

await page.locator('input[name^="lastName_"]').fill('Smith');

await page.locator('input[name^="passengerAge_"]').fill('25');

await page.locator('select[name^="passengerGender_"]').selectOption('female');

// Contact Details
await page.locator('#email').fill(process.env.EMAIL!);
await page.locator('#phone').fill(process.env.PHONE!);

// Continue to Payment
await page.locator('button[type="submit"]').click();

// Verify Payment Page
await expect(page).toHaveURL(/book\/payment/);

// Card Holder Name
await page.locator('#cardName').fill('Olivia Smith');

// Card Number
await page.locator('#cardNumber').fill('4111111111111111');

// Expiry
await page.locator('#cardExpiry').fill('12/30');

// CVV
await page.locator('#cardCvv').fill('123');

// Pay
await page.locator('button[type="submit"]').click();

// -------------------------------
// Booking Confirmation
// -------------------------------

// Verify Booking Confirmation Page
await expect(page.locator('[data-id="pnr"]')).toBeVisible();

// Verify Booking Status
await expect(page.locator('[data-id="state"]')).toHaveText(/Confirmed/i);

// Capture PNR
const pnr = (await page.locator('[data-id="pnr"]').textContent())?.trim();

console.log("PNR :", pnr);


const employeeId = process.env.EMPLOYEE_ID!;

expect(pnr).toMatch(new RegExp(`^TS-${employeeId}-\\d{4}$`));

// -------------------------------
// My Trips
// -------------------------------

await page.getByRole('link', { name: 'My Trips', exact: true }).click();

await expect(page).toHaveURL(/my-trips/);

// Verify the booking exists
await expect(page.locator(`[data-id="trip-${pnr}"]`)).toBeVisible();

// -------------------------------
// Cancel Booking
// -------------------------------

const bookedTrip = page.locator(`[data-id="trip-${pnr}"]`);

await bookedTrip.locator('button[type="submit"]').click();

// Verify that cancelling this booking changes its status to Refunded.
await expect(bookedTrip.locator('[data-id="state"]')).toHaveText(/Refunded/i);
});

});
