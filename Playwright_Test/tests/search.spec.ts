import { expect, test } from '@playwright/test';
import { SearchBar } from "../pages/SearchBar";


test.describe('Search Bar Tests', () => {
  test('should navigate to the home page and find search bar', async ({ page }) => {
    const searchBar = new SearchBar(page);
    await searchBar.goto();
    
    searchBar.searchbarFound();
    await expect(page).toHaveURL('https://www.nykaa.com/');
    await expect(page).toHaveTitle(/Nykaa/);
  });



  test('Positive: should enter a value in the searchbar and find a product', async ({ page }) => {
    const searchBar = new SearchBar(page);
    await searchBar.goto();
    searchBar.searchbarProductFound();
    await expect(page).toHaveURL('https://www.nykaa.com/hair-care/hair/shampoo/c/316?search_redirection=True');
    await expect(page.getByRole('heading', { name: 'Buy Shampoos Online, 1380' })).toBeVisible();
    
    // searchBar.searchbarUnknownProductFound();
    // await expect(page).toHaveURL('https://www.nykaa.com/search/result/?q=xyzz&root=search&searchType=Manual&sourcepage=Search+Page');
    // await expect(page.getByRole('heading', { name: 'Showing 12 of 12 results for xyzz' })).toBeVisible();

  })


  test('Negative: should enter a value in the searchbar and not find a product', async ({ page }) => {
    const searchBar = new SearchBar(page);
    await searchBar.goto();
    await searchBar.searchbarProductNotFound();
    await expect(page).toHaveURL('https://www.nykaa.com/search/result/?q=%40%23%24&root=search&searchType=Manual&sourcepage=home');
    await expect(page.getByAltText('no_result')).toBeVisible();
    await expect(page.getByText('Thanks for visiting our')).toBeVisible();
    // await expect(page.locator("#title", { hasText: 'Thanks for visiting our website!' })).toBeVisible();
    await expect(page.getByText("Unfortunately, we couldn’t find any matches for . Please try searching with another term.")).toBeVisible();
})

})

