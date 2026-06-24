import { test, expect } from "@playwright/test";

test.only("Verify the Base url of nykaa", async ({ page }) => {
  await page.goto("https://www.nykaa.com/");
  await expect(page).toHaveURL("https://www.nykaa.com/");
});
test.only("positive test for nykaa basic flow ", async ({ page }) => {
  await page.goto("https://www.nykaa.com/");
  await expect(page).toHaveURL("https://www.nykaa.com/");
  await expect(page.getByTitle("logo", { exact: true })).toBeVisible();
  await page.getByRole("textbox", { name: "Search on Nykaa" }).click();
  await expect(
    page.getByRole("textbox", { name: "Search on Nykaa" }),
  ).toBeVisible();
  await page.getByRole("textbox", { name: "Search on Nykaa" }).fill("lipstick");
  await page.getByRole("textbox", { name: "Search on Nykaa" }).press("Enter");
  const page1Promise = page.waitForEvent("popup");
  await page.getByRole("link", { name: "Nykaa Lip Grip No-Transfer" }).click();
  const page1 = await page1Promise;
});
test.only("negative test for nykaa basic flow ", async ({ page }) => {
  await page.goto("https://www.nykaa.com/");
  await expect(page).toHaveURL("https://www.nykaa.com/");
  await expect(page.getByTitle("logo", { exact: true })).toBeVisible();
  await page.getByRole("textbox", { name: "Search on Nykaa" }).click();
  await expect(
    page.getByRole("textbox", { name: "Search on Nykaa" }),
  ).toBeVisible();
  await page.getByRole("textbox", { name: "Search on Nykaa" }).fill("###");
  await page.getByRole("textbox", { name: "Search on Nykaa" }).press("Enter");
  await expect(
    page.getByText(
      "Unfortunately, we couldn’t find any matches for . Please try searching with another term.",
    ),
  ).toBeVisible();
});
