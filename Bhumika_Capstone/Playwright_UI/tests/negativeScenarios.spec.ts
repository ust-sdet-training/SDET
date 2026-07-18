import { test, expect } from "../fixtures/baseFixture";
import { SearchPage } from "../pages/SearchPage";
import { LoginPage } from "../pages/LoginPage";
import { ResultsPage } from "../pages/ResultsPage";

test.describe("Negative UI scenarios", () => {
  test("Login fails with invalid credentials", async ({ page }) => {
    const loginPage = new LoginPage(page);

    await loginPage.open();
    await loginPage.enterUsername("invalid-user@example.com");
    await loginPage.enterPassword("WrongPassword123!");
    await loginPage.clickLogin();

    await expect(page).toHaveURL(/\/login/);
  });

  test("Search rejects same origin and destination", async ({ page }) => {
    const searchPage = new SearchPage(page);
    const resultsPage = new ResultsPage(page);

    await searchPage.openFlightsSearch();
    await searchPage.selectFrom("PUN");
    await searchPage.selectTo("PUN");
    await searchPage.selectDepartureDate(7);
    await searchPage.selectReturnDate(14);
    await searchPage.searchFlights();

    await resultsPage.verifyNoFlightsFound();
  });

  test("Search blocks requests when destination is missing", async ({
    page,
  }) => {
    const searchPage = new SearchPage(page);
    const resultsPage = new ResultsPage(page);

    await searchPage.openFlightsSearch();
    await searchPage.selectFrom("PUN");
    await searchPage.searchFlights();

    await resultsPage.verifyNoFlightsFound();
  });
});
