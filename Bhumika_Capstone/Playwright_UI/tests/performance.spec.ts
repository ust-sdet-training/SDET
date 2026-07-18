import { test, expect } from "../fixtures/baseFixture";
import { SearchPage } from "../pages/SearchPage";

function getNavigationDuration() {
  const navEntry = performance.getEntriesByType("navigation")[0] as
    | PerformanceNavigationTiming
    | undefined;
  if (navEntry?.duration) {
    return navEntry.duration;
  }

  const timing = performance.timing;
  return timing.loadEventEnd - timing.navigationStart;
}

test.describe("UI performance checks", () => {
  test("Flights search page loads within acceptable time", async ({ page }) => {
    await page.goto("/flights/search");
    await page.waitForLoadState("networkidle");

    const loadTime = await page.evaluate(getNavigationDuration);
    expect(loadTime).toBeLessThanOrEqual(4000);
  });

  test("Round-trip search results page responds quickly", async ({ page }) => {
    const searchPage = new SearchPage(page);

    await searchPage.openFlightsSearch();
    await searchPage.selectFrom("PUN");
    await searchPage.selectTo("BOM");
    await searchPage.selectDepartureDate(7);
    await searchPage.selectReturnDate(14);

    await Promise.all([
      page.waitForLoadState("networkidle"),
      searchPage.searchFlights(),
    ]);

    const resultPageDuration = await page.evaluate(getNavigationDuration);
    expect(resultPageDuration).toBeLessThanOrEqual(5500);
  });

  test("Search page main content is interactive quickly", async ({ page }) => {
    await page.goto("/flights/search");
    await page.waitForLoadState("networkidle");

    const interactiveTime = await page.evaluate(() => {
      return (
        performance.timing.domInteractive - performance.timing.navigationStart
      );
    });

    expect(interactiveTime).toBeLessThanOrEqual(3000);
  });
});
