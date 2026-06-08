import { test, expect } from "@playwright/test";
import AxeBuilder from "@axe-core/playwright";

test("accessibility violations",async({ page }) => {
 
    await page.goto("/catalog");
    const results = await new AxeBuilder({page}).withTags(["wcag2a","wcag2aa"]).analyze();
    expect(results.violations).toEqual([]);
  });


// test("accessibility violations a11y page",async({ page }) => {
 
//     await page.goto("/a11y-lab");
//     const results = await new AxeBuilder({page}).withTags(["wcag2a","wcag2aa"]).analyze();
//     expect(results.violations).toEqual([]);
//   });

test("accessibility violations login page",async({ page }) => {
 
    await page.goto("/login");
    const results = await new AxeBuilder({page}).withTags(["wcag2a","wcag2aa"]).analyze();
    expect(results.violations).toEqual([]);
  });