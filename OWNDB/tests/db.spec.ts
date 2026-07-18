import { test, expect } from "@playwright/test";
import { getBooking } from "../databse/booking";

test("Verify Booking in DB", async () => {

    const booking: any = await getBooking("TS-1005-0001");

    expect(booking[0].pnr).toBe("TS-1005-0011");

    expect(booking[0].status).toBe("CONFIRMED");

    expect(booking[0].seat_no).toBe("S17");

    expect(Number(booking[0].amount)).toBe(472.50);

});