import { test, expect } from '@playwright/test';
import { SearchPage } from '../pages/SearchPage';

test('Search Bus', async ({ page }) => {
  const search=new SearchPage(page);
  
      await search.goto();
      await search.search();
});
