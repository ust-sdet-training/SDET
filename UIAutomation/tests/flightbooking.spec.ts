import { test, expect } from "../fixtures/evidence";
import { BookingFlowInput } from "../flow/BookingFlow";
import { env } from "../utils/env";

test("book a flight from BLR to CCU and verify it in My Trips", async ({ flow, evidence }) => {
  const input: BookingFlowInput = {
    credentials: env.credentials,
    search: env.search,
    flightLabel: env.flightLabel,
    seatDescription: env.seatDescription,
    passenger: env.passenger,
    card: env.card,
    couponLabel: env.couponLabel,
    bookingRefPrefix: env.bookingRefPrefix,
    myTripsSummary: env.myTripsSummary,
  };

  await flow.runFullBookingFlow(input);

  expect(evidence["bookingReference"]).toBeTruthy();
});