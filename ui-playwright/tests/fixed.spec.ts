import { test } from "../fixtures/app.fixture";

test("One Way Trip", async ({
    page,
    booking,
    bookingData,
    log,
    evidence
}) => {
    log.info("ONE WAY TRIP TEST STARTED");
    log.info("Launching TripStack application");
    await page.goto("/");
    log.info("Starting one-way booking flow");
    await booking.completeBooking(
        bookingData.login,
        bookingData.oneWay,
        bookingData.passenger,
        bookingData.payment
    );
    log.info("Booking completed successfully");
    evidence["Trip Details"] = bookingData.oneWay;
    evidence["Passenger Details"] = bookingData.passenger;
    evidence["Payment"] = {
        cardHolder: bookingData.payment.cardHolder
    };
    log.info("Evidence captured");
    log.info("ONE WAY TRIP TEST COMPLETED");
});
test("Return Trip", async ({
    page,
    booking,
    bookingData,
    log,
    evidence
}) => {
    log.info("RETURN TRIP TEST STARTED");
    log.info("Launching TripStack application");
    await page.goto("/");
    log.info("Starting return booking flow");
    await booking.completeBooking(
        bookingData.login,
        bookingData.returnTrip,
        bookingData.passenger,
        bookingData.payment
    );
    log.info("Return booking completed successfully");
    evidence["Trip Details"] = bookingData.returnTrip;
    evidence["Passenger Details"] = bookingData.passenger;
    evidence["Payment"] = {
        cardHolder: bookingData.payment.cardHolder
    };
    log.info("Evidence captured");
    log.info("RETURN TRIP TEST COMPLETED");
});