import { expect, test } from "../fixtures/app.fixture";

test.describe("Week05_Gate05  - Attaching Artifacts on Failure ", () => {

  test("Test to check the artifacts on failure",
    async ({ page }) => {

      await page.goto("/");

      // Verifing the heading
      await expect(page.getByRole("heading", {name: "This Product Does Not Exist"})).toBeVisible();

  });

});