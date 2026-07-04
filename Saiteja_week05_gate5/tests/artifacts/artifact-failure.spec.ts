import { expect, test } from "../../fixtures/app.fixture";

test.describe("Artifact Failure", () => {

  test("should attach artifacts on failure",
    async ({ page }) => {

      // Open the app home page to trigger the expected failure.
      await page.goto("/");

      // Assert that the missing product heading is shown.
      await expect(page.getByRole("heading", {name: "This Product Does Not Exist"})).toBeVisible();

  });

});