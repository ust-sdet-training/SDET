import AxeBuilder from '@axe-core/playwright';
import { test, expect } from '@playwright/test';

test.describe("a11y checks", () => {
    test("a11y check for serious and critical issues", async ({ page }) => {
        await page.goto("/");
        const a = await new AxeBuilder({ page })
            .withTags(["wcag2a", "wcag2aa"])
            .analyze();
        expect(a.violations.filter(v => v.impact === "critical" || v.impact === "serious")).toEqual([]);
    });
})