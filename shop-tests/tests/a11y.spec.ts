import AxeBuilder from '@axe-core/playwright';
import { expect, test } from "@playwright/test";

test.describe("Accessibility tests", () => {
    test("should have no accessibility violations on the home page", async ({ page }) => {
        await page.goto("/a11y-lab");
        const accessibilityScanResults = await new AxeBuilder({ page }).analyze();
        expect(accessibilityScanResults.violations.filter(v => v.impact === "critical" || v.impact === "serious")).toEqual([]);
    });
});