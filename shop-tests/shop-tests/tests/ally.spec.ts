import { test, expect } from "@playwright/test";
import AxeBuilder from "@axe-core/playwright";

test.describe("Accessibility tests", () => {
  test("home page should have no accessibility violation", async ({ page }) => {
    await page.goto("/");
    const accessibilityScanResults = await new AxeBuilder({ page }).analyze();
    expect(accessibilityScanResults.violations).toEqual([]);
  });
  test("wcag2a+wcag2aa should have no accessibility violation", async ({
    page,
  }) => {
    await page.goto("/products");
    const ac = await new AxeBuilder({ page })
      .withTags(["wcag2a", "wcag2aa"])
      .analyze();
    expect(ac.violations).toEqual([]);
  });
});
