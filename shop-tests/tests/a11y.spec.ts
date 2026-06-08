import { test, expect } from "@playwright/test";
import AxeBuilder from "@axe-core/playwright";

test.describe('accessibility test for shop', () => {
    test('login page', async ({page}) => {
        await page.goto('/login');

        const a11y = await new AxeBuilder({ page })
            .analyze();
        
        expect(a11y.violations.filter(v => 
            v.impact === 'critical')).toEqual([]);
    });
});