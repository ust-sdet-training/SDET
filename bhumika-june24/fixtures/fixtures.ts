import { test as base, Page } from '@playwright/test';
import { SearchPage } from '../pages/SearchPage';
import { ShopFlow } from '../flows/ShopFlow';

type Fixtures = {
  searchPage: SearchPage;
  shopFlow: ShopFlow;
};

export const test = base.extend<Fixtures>({
  searchPage: async ({ page }, use) => {
    await use(new SearchPage(page));
  },

  shopFlow: async ({ searchPage }, use) => {
    await use(new ShopFlow(searchPage));
  },
});

export const expect = test.expect;
