import { test } from '@playwright/test';
import { HomePage } from '../pages/HomePage';
import { SearchResultsPage } from '../pages/SearchResultsPage';
import { testData } from '../testdata/testData';

test.describe('Shoppers Stop Search', () => {
    test('Positive Search Scenario', async ({ page }) => {
        const homePage = new HomePage(page);
        const searchPage = new SearchResultsPage(page);

        await homePage.navigate();
        await homePage.verifyHomePageLoaded();
        await homePage.searchProduct(testData.validProduct);
        await searchPage.verifyResultsDisplayed();
    });
});