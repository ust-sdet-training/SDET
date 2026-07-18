import { getBooking } from "../databse/booking";
import { test, expect } from "../fixtures/fixtures";


test("Return Journey Booking", async ({
    login,
    bus,
    passenger,
    payment,
    myTrips
}) => {

    await login.login("erin@tripstack.test", "Password@123");


    await bus.searchBus(
        "BOM",
        "PUN",
        "2026-07-20"
    );

    await bus.selectSeat();
    await bus.searchBus(
        "PUN",
        "BOM",
        "2026-07-22"
    );
    await bus.selectSeat();

    await passenger.enterPassenger(
        "Erin",
        "Tripstack",
        "22",
        "erin@tripstack.test",
        "1234567890"
    );

    await payment.pay(
        "Erin Tripstack",
        "4111111111111111",
        "1233",
        "123"
    );

    await myTrips.verifyBooking();
    const pnr = await myTrips.getPNR();

    const booking: any = await getBooking(pnr);

    expect(booking[0].status).toBe("CONFIRMED");

});