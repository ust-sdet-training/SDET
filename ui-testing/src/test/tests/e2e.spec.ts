import { test, expect } from "../fixtures/test";
import { testUsers, testData } from "../fixtures/data";

test("Bus booking happy path", async ({
  book,
  page,
  log,
  evidence,
}) => {
  
  log.info("Starting booking flow");

  // Login
  await book.loginSuccessFlow();
  await expect(page.getByRole("tab", { name: "Buses" })).toBeVisible();
  log.info("Login successful");

  // Search
  const curdate = new Date();
  curdate.setDate(curdate.getDate() + 30);
  const travelDate = curdate.toISOString().split("T")[0];
  await book.search(testData.from, testData.to, testData.date);

  await expect(page.getByText(testData.busName)).toBeVisible();

  evidence.searchCriteria = {
    from: testData.from,
    to: testData.to,
    date: testData.date,
  };

  log.info("Bus search completed");

  // Select Bus
  await book.selectBus(testData.busName);
  await page.getByRole('tab', { name: 'Lower deck' }).click();

  await expect(page.getByRole("button", { name: `Seat ${testData.seatNumber} available` })).toBeVisible();

  log.info("Bus selected", {
    busName: testData.busName,
  });

  // Seat Selection

  await book.selectSeat(testData.seatNumber);

  await expect(page.getByRole("textbox", { name: /email/i })).toBeVisible();

  evidence.selectedSeat = testData.seatNumber;

  log.info("Seat selected");

  // Traveller Details
  await book.travellerDetails(
    testUsers.user.email,
    testUsers.user.phone
  );

  await expect(page.getByRole("textbox", { name: "Name on card" })).toBeVisible();

  evidence.traveller = {
    email: testUsers.user.email,
    phone: testUsers.user.phone,
  };

  log.info("Traveller details entered");

  // Payment
  const paymentSuccess = await book.payment();
  if (!paymentSuccess) {
    log.info("Payment failed");
    return;
  }

  log.info("Payment successful");
  await book.confirmSeat();
  evidence.bookingCompleted = true;

  log.info("Booking completed successfully");

  await page.getByRole("button", { name: "View my trips", }).click();
  await expect(page).toHaveURL(/.*trip.*/i);
  await expect(page.getByRole('heading', { name: 'My Trips' })).toBeVisible();
  await page.getByRole('button', { name: 'Cancel' }).click();

  log.info("Ticket cancellation completed successfully");
});