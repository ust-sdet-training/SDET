import { test, expect } from '../fixtures/tripstack.fixture';
import { BusSearchPage } from '../pages/bus-search.page';
import { BusResultsPage } from '../pages/bus-results.page';
import { dateInIndiaAfter, journey } from '../support/journey';

test.describe(' bus search', () => {
  test('finds AC Semi-Sleeper buses from HYD to BOM 14 days from today', async ({ page }) => {
    const search = new BusSearchPage(page);
    const results = new BusResultsPage(page);

    await search.goto();
    await search.searchRoute(journey.from, journey.to, dateInIndiaAfter(journey.daysFromToday));

    await results.expectRoute(journey.from, journey.to);
    const acSemiSleeper = await results.filterByAcSemiSleeper();
    await expect(acSemiSleeper).toContainText(/A\/C Semi-Sleeper/i);
  });

  test('opens seat selection for an AC Semi-Sleeper bus', async ({ page }) => {
    const search = new BusSearchPage(page);
    const results = new BusResultsPage(page);

    await search.goto();
    await search.searchRoute(journey.from, journey.to, dateInIndiaAfter(journey.daysFromToday));
    const acSemiSleeper = await results.filterByAcSemiSleeper();
    await results.selectSeats(acSemiSleeper);
  });
});
