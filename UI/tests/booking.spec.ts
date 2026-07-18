// import { test, expect } from "@playwright/test";

// import { TripStackFlow } from "../flows/TripStackFlow";

// test.describe("TripStack Booking Flow", () => {

//     test("Complete Flight Booking", async ({ page }) => {

//         const flow = new TripStackFlow(page);

//         await flow.completeBooking();

//         const pnr =
//             await flow.booking.getPNR();

//         console.log("PNR :", pnr);

//         expect(
//             pnr.startsWith("TS-1021-")
//         ).toBeTruthy();

//         expect(
//             await flow.booking.getBookingStatus()
//         ).toBe("CONFIRMED");

//         await flow.booking.openMyTrips();

//     });

// });

import { test, expect } from "@playwright/test";

import { TripStackFlow } from "../flows/TripStackFlow";
import { BookingData } from "../test-data/BookingData";

test.describe("TripStack Booking Flow", () => {

    test("Complete Single Flight Booking", async ({ page }) => {

        const flow = new TripStackFlow(page);

        await flow.completeBooking();

        const pnr = await flow.booking.getPNR();

        console.log("PNR :", pnr);

        expect(pnr.startsWith("TS-1021-")).toBeTruthy();

        expect(
            await flow.booking.getBookingStatus()
        ).toBe("CONFIRMED");

        await flow.booking.openMyTrips();

    });

    test("Same passenger books two flights", async ({ page }) => {

        const flow = new TripStackFlow(page);

        await flow.completeTwoBookings();

        console.log("First PNR :", BookingData.firstPNR);
        console.log("Second PNR :", BookingData.secondPNR);

        expect(
            BookingData.firstPNR.startsWith("TS-1021-")
        ).toBeTruthy();

        expect(
            BookingData.secondPNR.startsWith("TS-1021-")
        ).toBeTruthy();

        expect(
            BookingData.firstPNR
        ).not.toBe(
            BookingData.secondPNR
        );

        expect(
            BookingData.firstPNR.length
        ).toBeGreaterThan(0);

        expect(
            BookingData.secondPNR.length
        ).toBeGreaterThan(0);

        await flow.booking.openMyTrips();

        await flow.myTrips.verifyPageLoaded();

        await flow.myTrips.verifyBookingExists(
            BookingData.firstPNR
        );

        await flow.myTrips.verifyBookingExists(
            BookingData.secondPNR
        );

        await flow.myTrips.verifyBookingStatus(
            BookingData.firstPNR,
            "CONFIRMED"
        );

        await flow.myTrips.verifyBookingStatus(
            BookingData.secondPNR,
            "CONFIRMED"
        );

        await flow.myTrips.printAllBookings();

    });

});