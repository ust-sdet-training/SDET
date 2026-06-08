import {test, expect} from "../fixtures/test";

test.describe('catalog page', () => {
    test('search success', async ({ shop, page }) => {
        await shop.searchQuery('running');
        await expect(page.getByTestId('catalog-result-count')).toHaveText(/1/);
    });

    test('search failed', async ({ shop, page }) => {
        await shop.searchQuery('234');
        await expect(page.getByTestId('catalog-result-count')).toHaveText(/0/);
    });
});