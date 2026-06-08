import { expect, test } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';
import { ProductPage } from '../pages/ProductPage';
import { CheckoutPage } from '../pages/CheckoutPage';
import { SearchPage } from '../pages/SearchPage';
 
test('Login with invalid credentials', async ({ page }) => {
    const loginPage = new LoginPage(page);
    await loginPage.open();
    await loginPage.login('invalid@test.com','WrongPassword');
    await expect(page.getByText('Invalid credentials')).toBeVisible();
    await expect(page).toHaveURL(/\/login$/);
});
 
test("Search with empty value on the product page", async ({ page }) => {
    const searchPage = new SearchPage(page);
    await page.goto("/catalog");
    await searchPage.search("");
    await expect(searchPage.results()).toContainText("12");
});
test('Search the product that doesnt exist', async ({ page }) => {
    const searchPage = new SearchPage(page);
    await page.goto('/catalog');
    await searchPage.searchNoResults('abcdef');
});

