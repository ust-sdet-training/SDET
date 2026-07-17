import { test, expect } from "../fixtures/baseFixture";
import { SearchPage } from "../pages/SearchPage";
import { TripFlow } from "../flows/TripFlow";
import { FlightData } from "../data/FlightData";
import { Users } from "../data/Users";

test("Bhumika round-trip flight search and results verification", async ({
  page,
}) => {
  const searchPage = new SearchPage(page);

  await searchPage.openFlightsSearch();
  await searchPage.searchRoundTrip(FlightData.roundTrip);

  await expect(page).toHaveURL(/\/flights\/results/);
});

test("Bhumika complete round-trip flight flow", async ({ page }) => {
  const tripFlow = new TripFlow(page);

  await tripFlow.completeFlightJourney(
    Users.employee.email,
    Users.employee.password,
  );

  await expect(page).toHaveURL(/\/ticket|\/confirmation|\/booking/i);
});
