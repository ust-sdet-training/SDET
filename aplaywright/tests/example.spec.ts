import { test, expect } from '@playwright/test';

test.describe("Ex1: Happy Path", ()=>{
  test("basic UI scenario", async({ page })=>{
    await page.goto("https://www.shoppersstop.com/");

    await expect(page.getByRole('img', { name: /home/i })).toBeVisible();
    await expect(page.getByPlaceholder('Search')).toBeVisible();
    await page.getByPlaceholder('Search').fill("watch");
    await page.getByRole('img', { name: /search/i }).click();

    await expect(page.getByText('Watches')).toBeVisible();
  });

  test("negative UI scenario", async({ page })=>{
    await page.goto("https://www.shoppersstop.com/");

    await expect(page.getByRole('img', { name: /home/i })).toBeVisible();
    await expect(page.getByPlaceholder('Search')).toBeVisible();
    await page.getByPlaceholder('Search').fill("watch");
    await page.getByRole('img', { name: /search/i }).click();

    await expect(page.getByText('Watches')).toBeVisible();
  });
});