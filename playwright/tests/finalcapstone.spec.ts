import { test, expect } from "../fixtures/shopfixtures";
import { Util } from "../src/utils";
import { Secrets } from "../src/secrets";
import { userdata } from "../testdata/userdata";
import { redactSensitiveFields } from "../src/logger";
test('Tripstack Validation', async ({ trip, log,evidence,page }) => {

   log.info("Logging in");

   const loginStart = performance.now();
   await trip.makeALoginWith(
      Util.emailName(userdata.user1.firstname),
      Secrets.get(`TRIPSTACK_${userdata.user1.firstname}_PASSWORD`)
   );
  await trip.makeALoginWith(Util.emailName(userdata.user1.firstname),Secrets.get(`TRIPSTACK_${userdata.user1.firstname}_PASSWORD`));
   log.info("Login successful");
   const loginTime = performance.now() - loginStart;
   log.info(`Login Time: ${loginTime} ms`);

   log.info(`Selecting route: ${userdata.busDetails.from} -> ${userdata.busDetails.to}`);
   await trip.selectTheRoute(userdata.busDetails.from,userdata.busDetails.to);
   log.info("Route selected");
   log.info(`Booking for ${userdata.busDetails.days} day(s)`);
   await trip.bookFor(userdata.busDetails.days);
   log.info("Travel date selected");

     const searchStart = performance.now();
     
   log.info("Searching buses...");
   await trip.searchBus();
   log.info("Bus search completed");

   const searchTime = performance.now() - searchStart;
      log.info(`Login Time: ${searchTime} ms`);

   log.info(`Opening ${userdata.busDetails.bus_kind} bus listing page`);
   await trip.goToBusListingPageFor(userdata.busDetails.bus_kind);

   const listingStart = performance.now();


   log.info("Bus listing page opened");

   log.info(`Selecting seat on ${userdata.busDetails.deck} deck`);
   await trip.selectAvailableSeat(userdata.busDetails.deck);
   log.info("Seat selected");
   const listingTime = performance.now() - listingStart;
      log.info(`Login Time: ${listingTime} ms`);

   log.info("Navigating to passenger details page");
   await trip.goToPassengerDetails();
   log.info("Passenger details page opened");

   log.info(
  `Entering passenger details for ${userdata.user1.firstname} ${userdata.user1.lastname}`);
   await trip.makeAPaymentFor(userdata.busDetails.deck,userdata.user1.firstname,userdata.user1.lastname,userdata.user1.age,userdata.user1.gender, Util.emailName(userdata.user1.firstname),userdata.user1.phone);
   log.info("Passenger details entered successfully");
const safeCardDetails = redactSensitiveFields(userdata.cardDetails);
  
try {
    await trip.paywith(
        userdata.cardDetails.cardName,
        userdata.cardDetails.cardNumber,
        userdata.cardDetails.expiry,
        userdata.cardDetails.cvv
    );

    const bookingStatus = await trip.validateBookingStatus();

    expect(bookingStatus).toContain("CONFIRMED");

    log.info(`Booking Status: ${bookingStatus}`);

} catch (bookingError) {

    log.warn(`Booking validation failed: ${bookingError}`);

    try {
        const serverError = await trip.validateThePayement500();

        expect(serverError).toBe("payment gateway error (5xx)");

        log.info("Payment Gateway Error validated successfully");

    } catch (paymentError) {

        log.error(`Payment validation failed: ${paymentError}`);

        throw paymentError;
    }
}
});