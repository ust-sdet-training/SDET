import { test, expect } from '@playwright/test';
import { SearchPage } from '../pages/SearchPage';

test('Search Page Test', async ({ page }) => {
    const search=new SearchPage(page);
    await search.search();
});

test("Search for a product which is not present",async({page})=>{
   const searchNot=new SearchPage(page);
   await searchNot.searchNotProduct();
 
})