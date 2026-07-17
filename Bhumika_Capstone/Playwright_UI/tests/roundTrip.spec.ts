import { test, expect } from "../fixtures/baseFixture";
import { SearchPage } from "../pages/SearchPage";

test("Bhumika round-trip flight search", async ({ page }) => {
  const searchPage = new SearchPage(page);

  await searchPage.openFlightsSearch();
  await searchPage.selectFrom("PUN");
  await searchPage.selectTo("BOM");
  await searchPage.selectDepartureDate(7);
  await searchPage.searchFlights();

  await expect(page).toHaveURL(/\/flights\/results/);
});
