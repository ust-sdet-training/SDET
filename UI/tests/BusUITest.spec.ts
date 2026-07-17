import { test } from '../fixtures/trip';
import dotenv from 'dotenv';

dotenv.config();

const email = process.env.EMAIL!;
const password = process.env.PASSWORD!;
const first_name = process.env.FIRST_NAME!;
const last_name = process.env.LAST_NAME!;
const card_name = process.env.CARD_NAME!;
const card_number = process.env.CARD_NUMBER!;
const expiry = process.env.CARD_EXPIRY!;
const cvv = process.env.CVV!;
const age = process.env.AGE!;
const phone = process.env.PHONE!;

test.describe("Capstone", () => {
    test("Book a sleeper bus ticket", async ({ trip }) => {
        await trip.validLogin(email, password);
        await trip.searchForBuses("Ahmedabad AMD", "Delhi DEL", "2026-08-02");
        await trip.selectSeatsForFirstBus("Seat L1 ladies");
        await trip.fillPassengerDetails(first_name, last_name, age, email, phone);
        await trip.fillPaymentDetails(card_name, card_number, expiry, cvv);
        await trip.verifyBookingIsConfirmed();
        await trip.cancelTrip();
    });
});