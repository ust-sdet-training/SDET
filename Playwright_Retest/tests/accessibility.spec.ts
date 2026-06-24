import { test, expect } from '@playwright/test';
import AxeBuilder from '@axe-core/playwright';
test('Home page accessibility',
async ({ page }) => {
    await page.goto('https://www.nykaa.com/');
    const results =await new AxeBuilder({page}).analyze();
    expect(results.violations).toEqual([]);
});