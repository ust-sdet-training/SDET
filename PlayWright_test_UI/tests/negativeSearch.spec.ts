import { test } from '@playwright/test';
import { HomePage } from '../pages/HomePage';
import { SearchResultsPage } from '../pages/SearchResultsPage';

test('Negative Search Scenario', async ({ page }) => {

    const homePage = new HomePage(page);
    const searchPage = new SearchResultsPage(page);

    await homePage.navigate();

    await homePage.searchProduct('xyz123invalidproduct');

    await searchPage.verifyNoResultsMessage();
});