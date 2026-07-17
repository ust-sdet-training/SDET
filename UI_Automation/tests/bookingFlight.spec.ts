import { test, expect } from "../fixtures/ScenarioFixture";


test.describe("Final Capstone: TripStack Scenario", () => {
    test.beforeEach("Opening Page", async ({page}) => {
        await page.goto("/");
    })

    test("@smoke Navigating to flight", async ({page, home}) => {
        await home.goToHomePage();
        await home.verifyUserOnFlightsTab();
        await home.fillingTravelDetails("Lucknow", "Delhi", 20);
    })

    test("@smoke Navigating & Booking flight", async ({log, evidence, login, home, 
        flights, flight, passenger, payment, confirmation, mytrips}) => {
        
        log.info("Launching TripStack home page");
        await home.goToHomePage();

        log.info("Verified Flights tab is active");
        await home.verifyUserOnFlightsTab();

        log.info("Searching flights",
            {
                origin: "Lucknow",
                destination: "Delhi",
                travelInDays: 20
            }
        );
        await home.fillingTravelDetails(
            "Lucknow",
            "Delhi",
            20
        );

        log.info(
            "Validating initial flight search results",{expectedFlights: 8}
        );
        await flights.verifyflightCount(8);

        log.info("Validating flight count matches rendered cards");
        await flights.verifyflightCountAndCardCountMatch();

        log.info("Opening flight details page");
        await flights.goToFlightDetailsPage();

        log.info("Selecting seat",{seat: "1D"});
        const selectedSeat = await flight.bookSeat();

        log.info("Verifying selected seat",{seat:selectedSeat});
        await flight.verifySeatisSelected();
        await flight.verfiySeatIsBooked(selectedSeat);

        log.info("Navigating to passenger details page");
        await flight.continueToPassengerDetails();

        log.info("Authenticating user");
        await login.userLogin();

        log.info("Entering passenger details",
            {
                seat: selectedSeat,
                passenger: "Mallory Thomas"
            }
        );
        await passenger.fillDetails(
            selectedSeat,
            "Mallory",
            "Thomas",
            "18",
            "Female"
        );

        log.info("Entering passenger Contact details",
            {
                email: "mallory@tripstack.test",
                mobile: "9802373739"
            }
        );
        await passenger.fillContactDetails(
            "mallory@tripstack.test", "9802373739"
        )

        log.info("Proceeding to payment");
        await passenger.goToPayment();

        log.info("Entering payment details");
        await payment.enterPaymentDetails("Mallory");

        log.info("Completing payment transaction");
        await payment.completePayment();

        log.info("Verifying booking confirmation");
        await confirmation.verifyBadgeShowsConfirmed();

        const ticket_pnr = await confirmation.checkPNR_Number();
        log.info("Booking confirmed", {pnr: ticket_pnr});

        log.info("Navigating to My Trips");
        await confirmation.viewMyTrips();

        log.info("Verifying booking exists in My Trips");
        await mytrips.verifyTicketisBooked();

        log.info("Verifying booking PNR", {pnr: ticket_pnr});

await mytrips.verifyBookingTitle(ticket_pnr);
        
        log.info("Verifying booking status",{status: "CONFIRMED"});
        await mytrips.verifyBookingStatus(
            "CONFIRMED"
        );

        log.info("Verifying booked seat",{seat: selectedSeat});
        await mytrips.verifyBookingSeat(selectedSeat);

        log.info("Flight booking journey completed successfully",
            {
                pnr: ticket_pnr,
                seat: "1D",
                status: "CONFIRMED"
            }
        );

    })
})

