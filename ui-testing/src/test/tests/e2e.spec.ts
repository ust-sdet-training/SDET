import { test, expect } from "../fixtures/test";
import { testUsers } from "../fixtures/test-users";

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
  curdate.setDate(curdate.getDate()+30);
  const travelDate = curdate.toISOString().split("T")[0];
  await book.search("BOM", "DEL", "2026-08-17");

  await expect(page.getByText("Kallada Travels")).toBeVisible();

  evidence.searchCriteria = {
    from: "BOM",
    to: "DEL",
    date: "2026-08-17",
  };

  log.info("Bus search completed");

  // Select Bus
  await book.selectBus("Kallada Travels");

  await expect(page.getByRole("button", { name: "Seat L3 available" })).toBeVisible();

  log.info("Bus selected", {
    busName: "Kallada Travels",
  });

  // Seat Selection
  await book.selectSeat();

  await expect(page.getByRole("textbox", { name: /email/i })).toBeVisible();

  evidence.selectedSeat = "L3";

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
  await book.payment();

  await expect(page.getByRole("button", { name: "View my trips",})).toBeVisible();
  log.info("Payment successful");
  await book.confirmSeat();
  await expect(page).toHaveURL(/.*trip.*/i);
  evidence.bookingCompleted = true;

  log.info("Booking completed successfully");
});