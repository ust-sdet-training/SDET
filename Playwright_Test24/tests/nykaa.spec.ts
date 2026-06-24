import { test } from '@playwright/test';
import { HomePage } from '../pages/HomePage';


test('Happy Path - Search available product', async ({ page }) => {
    const home = new HomePage(page);
    await home.openHomePage();
    await home.searchProduct('pears soap');
    await home.verifySearchResult('pears soap');
});


test('Negative Path - Search unavailable product', async ({ page }) => {
    const home = new HomePage(page);
    await home.openHomePage();
    await home.searchProduct('InvalidProduct12345');
    await home.verifyNoResult();
});