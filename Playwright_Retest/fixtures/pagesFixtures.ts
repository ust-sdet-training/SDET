import {test as base} from "@playwright/test";
import { HomePage } from "../pages/HomePage";
import { ProductPage } from "../pages/Productpage";
import { SearchPage } from "../pages/SearchPage";


type pagefixtures={homepage:HomePage,productpage:ProductPage,searchpage:SearchPage};

export const test=base.extend<pagefixtures>({
    homepage: async ({ page }, use) => {
        await use(new HomePage(page));
    },
    productpage:async ({ page },use)=>{
        await use(new ProductPage(page));
    },
    searchpage:async ({ page },use)=>{
        await use(new SearchPage(page));
    }

})
export { expect } from '@playwright/test';
