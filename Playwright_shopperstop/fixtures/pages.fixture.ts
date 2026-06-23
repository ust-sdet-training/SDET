import { test as base } from "@playwright/test";
import { HomePage } from "../pages/HomePage";
import { ProductPage } from "../pages/ProductPage";
import { SearchPage } from "../pages/SearchPage";

type PageFixtures = {
    homePage: HomePage;
    searchPage: SearchPage;
    productPage: ProductPage;
};

export const test = base.extend<PageFixtures>({
    homePage: async ({ page }, use) => {
        await use(new HomePage(page));
    },
    searchPage: async ({ page }, use) => {
        await use(new SearchPage(page));
    },
    productPage: async ({ page }, use) => {
        await use(new ProductPage(page));
    },
});

export { expect } from "@playwright/test";
