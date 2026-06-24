import { test } from '@playwright/test';
import { NykaaPage } from '../pages/NykaaPage';
import { testData } from '../fixtures/testData';
import { Helper } from '../utils/helper';

test.describe('Nykaa UI Automation', () => {

  
    test('TC_001 Verify Home Page', async ({ page }) => {
        const nykaa = new NykaaPage(page);

        await nykaa.navigate(testData.url);
        await nykaa.verifyHomePage();
    });

  
    test('TC_002 Verify Valid Product Search', async ({ page }) => {
        const nykaa = new NykaaPage(page);

        await nykaa.navigate(testData.url);
        await nykaa.searchProduct(testData.validProduct);

        await Helper.wait(3);
    });

   
    test('TC_003 Verify Search Result Visibility', async ({ page }) => {
        const nykaa = new NykaaPage(page);

        await nykaa.navigate(testData.url);
        await nykaa.searchProduct(testData.validProduct);

        await Helper.wait(3);

        await nykaa.verifySearchResult(testData.validProduct);
    });

  
    test('TC_004 Verify Invalid Product Search', async ({ page }) => {
        const nykaa = new NykaaPage(page);

        await nykaa.navigate(testData.url);
        await nykaa.searchProduct(testData.invalidProduct);

        await Helper.wait(3);

        await nykaa.verifySearchBoxVisible();
    });


    test('TC_005 Verify Empty Search Validation', async ({ page }) => {
        const nykaa = new NykaaPage(page);

        await nykaa.navigate(testData.url);
        await nykaa.searchProduct(testData.emptySearch);

        await nykaa.verifyEmptySearch();
    });

});