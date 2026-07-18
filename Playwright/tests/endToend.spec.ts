import { test, expect } from "../fixtures/fixture";

import { BookingFlow } from "../flows/BookingFlow";

import { logger } from "../logger/logger";

test("Complete Booking Journey", async ({ page }) => {

    const bookingFlow = new BookingFlow(page);

    logger.info("Starting Booking Flow");

    await bookingFlow.completeBooking();

    await bookingFlow.verifyBooking();

    const bookingRef = await bookingFlow.getBookingReference();

    logger.info(`Booking Reference : ${bookingRef}`);

    expect(bookingRef).toContain("TS");

});