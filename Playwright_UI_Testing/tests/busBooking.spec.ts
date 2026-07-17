import { test } from "../fixtures/baseFixture";
import { Users } from "../data/Users";
import { BusData } from "../data/BusData";
import { PaymentData } from "../data/PaymentData";

test.describe("Bus Booking", () => {

    test("User should book a bus successfully", async ({ busBookingFlow }) => {

        await busBookingFlow.login(
            Users.username,
            Users.password
        );

        await busBookingFlow.searchBus(
            BusData.from,
            BusData.to,
            BusData.date
        );

        await busBookingFlow.selectBus();

        await busBookingFlow.selectSeat(
            BusData.seatNumber
        );

        await busBookingFlow.enterPassengerDetails(
        BusData.firstName,
        BusData.lastName,
        BusData.age,
        BusData.gender,
        Users.email,
        Users.phone
    );

        await busBookingFlow.makePayment(
            PaymentData.cardName,
            PaymentData.cardNumber,
            PaymentData.expiry,
            PaymentData.cvv
        );

        await busBookingFlow.verifyBooking();

        await busBookingFlow.openMyTrips();

    });

});