import { test, expect } from "../fixtures/app"

test('adding product to cart', async ({ page, log }) => {
    await page.goto('/catalog');
    await expect(page.getByRole('heading', { name: 'Product Catalog' })).toBeVisible();
    log.info('Heading visible');

    const productSearchForm = page.getByRole('form', { name: 'Product filters' });

    await productSearchForm.getByLabel('Search products').fill('travel');
    await page.getByRole('button', { name: 'Search' }).click();

    await expect(page.getByTestId('catalog-result-count')).toContainText('3');
    log.info('Product visible');


    await productSearchForm.getByLabel('Search products').pressSequentially('')

    await page.getByRole('link', { name: 'Travel Backpack' }).click();
    await expect(page).toHaveURL(/\/product\/travel-backpack/);
    await expect(page.getByRole('heading', { name: 'Travel Backpack' })).toBeVisible();
    log.info('PDP visible');


    await page.getByRole('button', { name: 'Add to cart' }).click();
    await expect(page).toHaveURL(/\/cart/);
    await expect(page.getByRole('heading', { name: 'Cart' })).toBeVisible();
    await expect(page.getByTestId('cart-count')).toHaveText('1');
    log.info('Added to cart visible');

});