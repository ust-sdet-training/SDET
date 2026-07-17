import { test, expect } from "../fixtures/baseFixture";
import { Users } from "../data/Users";
import { BusData } from "../data/BusData";
import { PaymentData } from "../data/PaymentData";

test.describe("Bus Booking - Positive, Negative & Edge Cases", () => {


    test("TC01 - Invalid Login", async ({ loginPage, page }) => {

        await loginPage.open();

        await loginPage.login(
            Users.username,
            "WrongPassword"
        );

        await expect(
            page.getByText(/Wrong credentials/i)
        ).toBeVisible();
    });

    test("TC02 - Empty Login", async ({ loginPage, page }) => {

        await loginPage.open();

        await loginPage.clickLogin();

        await expect(
            page.getByText(/required/i)
        ).toBeVisible();
    });

    test("TC03 - Same Source and Destination", async ({ busBookingFlow, page }) => {

        await busBookingFlow.login(
            Users.username,
            Users.password
        );

        await busBookingFlow.searchBus(
            "Lucknow",
            "Lucknow",
            BusData.date
        );

        await expect(
            page.getByText(/same/i)
        ).toBeVisible();
    });

    test("TC04 - Empty First Name", async ({ busBookingFlow, passengerDetailsPage, page }) => {

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
        await busBookingFlow.selectSeat(BusData.seatNumber);

        await passengerDetailsPage.enterPassengerDetails(
            "",
            BusData.lastName,
            BusData.age,
            BusData.gender,
            Users.email,
            Users.phone
        );

        await expect(
            page.getByText(/required/i)
        ).toBeVisible();
    });

    test("TC05 - Invalid Age", async ({ busBookingFlow, passengerDetailsPage, page }) => {

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
        await busBookingFlow.selectSeat(BusData.seatNumber);

        await passengerDetailsPage.enterPassengerDetails(
            BusData.firstName,
            BusData.lastName,
            "150",
            BusData.gender,
            Users.email,
            Users.phone
        );

        await expect(
            page.getByText(/invalid/i)
        ).toBeVisible();
    });

    test("TC06 - Invalid Email", async ({ busBookingFlow, passengerDetailsPage, page }) => {

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
        await busBookingFlow.selectSeat(BusData.seatNumber);

        await passengerDetailsPage.enterPassengerDetails(
            BusData.firstName,
            BusData.lastName,
            BusData.age,
            BusData.gender,
            "abc",
            Users.phone
        );

        await expect(
            page.getByText(/email/i)
        ).toBeVisible();
    });

    test("TC07 - Invalid Card", async ({ busBookingFlow, paymentPage, page }) => {

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
        await busBookingFlow.selectSeat(BusData.seatNumber);

        await busBookingFlow.enterPassengerDetails(
            BusData.firstName,
            BusData.lastName,
            BusData.age,
            BusData.gender,
            Users.email,
            Users.phone
        );

        await paymentPage.makePayment(
            PaymentData.cardName,
            "1111111111111111",
            PaymentData.expiry,
            PaymentData.cvv
        );

        await expect(
            page.getByText(/payment/i)
        ).toBeVisible();
    });

    test("TC08 - Expired Card", async ({ busBookingFlow, paymentPage, page }) => {

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
        await busBookingFlow.selectSeat(BusData.seatNumber);

        await busBookingFlow.enterPassengerDetails(
            BusData.firstName,
            BusData.lastName,
            BusData.age,
            BusData.gender,
            Users.email,
            Users.phone
        );

        await paymentPage.makePayment(
            PaymentData.cardName,
            PaymentData.cardNumber,
            "01/20",
            PaymentData.cvv
        );

        await expect(
            page.getByText(/expired/i)
        ).toBeVisible();
    });

    test("TC09 - Cancel Booking", async ({ busBookingFlow, myTripsPage }) => {

        await busBookingFlow.login(
            Users.username,
            Users.password
        );

        await busBookingFlow.openMyTrips();

        await myTripsPage.cancelBooking();

        await myTripsPage.verifyBookingStatus("REFUNDED");
    });

});