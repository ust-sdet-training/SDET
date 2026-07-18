import { getBooking } from "../databse/booking";
import { test, expect } from "../fixtures/fixtures";

test("Return Journey", async ({
    login,
    bus,
    passenger,
    payment,
    myTrips
}) => {

    await login.login(
        "erin@tripstack.test",
        "Password@123"
    );

    await bus.searchBus();

    await bus.selectSeat();

    await passenger.enterPassenger();

    await payment.pay();

    await myTrips.verifyBooking();

    const pnr = await myTrips.getPNR();

    const booking: any = await getBooking(pnr);

    expect(booking[0].status).toBe("CONFIRMED");
});