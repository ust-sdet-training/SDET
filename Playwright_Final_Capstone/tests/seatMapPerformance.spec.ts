import { expect, test } from '../fixtures/testFixtures';
import { config } from '../utils/config';

test.describe('Seat-map performance gate', () => {
  test('enforces a seat-map render threshold before checkout continues @performance', async ({
    appPages,
    bookingData,
    user,
  }) => {
    await appPages.login.open();
    await appPages.login.login(user.email, user.password);

    await appPages.flightSearch.open();
    await appPages.flightSearch.searchFlights(bookingData);

    await expect(appPages.flightResults.flight(bookingData.flightName)).toBeVisible();

    const seatMapStartedAt = Date.now();
    await appPages.flightResults.bookFlight(bookingData.flightName);
    await appPages.seatSelection.selectFirstAvailableSeat();

    const seatMapRenderMs = Date.now() - seatMapStartedAt;

    expect(
      seatMapRenderMs,
      `Seat map exceeded ${config.seatMapMaxRenderMs} ms`,
    ).toBeLessThanOrEqual(config.seatMapMaxRenderMs);
  });
});
