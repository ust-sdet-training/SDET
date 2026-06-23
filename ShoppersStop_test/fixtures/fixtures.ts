import { test as base, expect } from '@playwright/test';
import { HomePage } from '../pages/homePage';
import { SearchPage } from '../pages/SearchPage';
import { ProductPage } from '../pages/ProductPage';

type MyFixtures = {
    homePage: HomePage;
    searchPage: SearchPage;
    productPage: ProductPage;

};

export const test = base.extend<MyFixtures>({

    homePage: async ({ page }, use) => {
        await use(new HomePage(page));
    },

    searchPage: async ({ page }, use) => {
        await use(new SearchPage(page));
    },

    productPage: async ({ page }, use) => {
        await use(new ProductPage(page));
    }

});

export { expect };