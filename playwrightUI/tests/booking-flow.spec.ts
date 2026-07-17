import { test } from '../fixtures/tripstack.fixture';
import { LandingPage } from '../pages/landing.page';
import { BusSearchPage } from '../pages/bus-search.page';
import { BusResultsPage } from '../pages/bus-results.page';
import { SeatSelectionPage } from '../pages/seat-selection.page';
import { PassengerDetailsPage } from '../pages/passenger-details.page';
import { PaymentPage } from '../pages/payment.page';
import { BookingConfirmationPage } from '../pages/booking-confirmation.page';
import { MyTripsPage } from '../pages/my-trips.page';
import { dateInIndiaAfter, journey } from '../support/journey';

test.describe('complete bus booking', () => {
  
  test('books HYD to BOM AC Semi-Sleeper and verifies its PNR in My Trips', async ({ page, loginPage }) => {
    test.setTimeout(90_000);
    await loginPage.goto();
    await loginPage.login();
    await new LandingPage(page).openBuses();

    const search = new BusSearchPage(page);
    const results = new BusResultsPage(page);
    await search.searchRoute(journey.from, journey.to, dateInIndiaAfter(journey.daysFromToday));
    await results.expectRoute(journey.from, journey.to);
    await results.selectSeats(await results.filterByAcSemiSleeper());

    const seatMapUrl = page.url();
    let reachedPayment = false;
    for (let attempt = 0; attempt < 3 && !reachedPayment; attempt += 1) {
      if (attempt > 0) await page.goto(seatMapUrl);
      const seats = new SeatSelectionPage(page);
      await seats.selectAvailableSeat(attempt);
      await seats.continueToPassengerDetails();

      const passenger = new PassengerDetailsPage(page);
      await passenger.enterDetails();
      reachedPayment = (await passenger.submit()) === 'payment';
    }
    if (!reachedPayment) throw new Error('No available seat could be held after three attempts.');
    await new PaymentPage(page).pay();

    const pnr = await new BookingConfirmationPage(page).pnrFor(journey.employeeId);
    const myTrips = new MyTripsPage(page);
    await myTrips.goto();
    await myTrips.expectPnr(pnr);
  });
});
