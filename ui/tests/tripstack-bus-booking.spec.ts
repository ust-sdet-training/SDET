import { test, expect } from '../fixtures/playwrightFixtures';
import { bookTrip } from '../flows/tripstackFlow';
import { getTestData } from './testData';

test.setTimeout(180_000);

test('books a bus ticket for employee 1018 from BLR to HYD for seat S4', async ({ loginPage, busSearchPage, resultsPage, seatMapPage, passengerPage, paymentPage, confirmationPage, logger, page }) => {
  const { credentials, journey, passenger, payment, empId } = getTestData();

  logger.info('Test started');
  const pnr = await bookTrip(
    { loginPage, busSearchPage, resultsPage, seatMapPage, passengerPage, paymentPage, confirmationPage },
    credentials,
    journey,
    passenger,
    payment,
    empId,
    logger
  );

  await expect(page.getByRole('heading', { name: /you're all set/i })).toBeVisible();
  await expect(page.locator('body')).toContainText(/booking reference|confirmed/i);
  logger.info('Test completed', { pnr });
});
