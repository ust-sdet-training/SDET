import { CartPage } from './../pages/CartPage';
import { Page } from '@playwright/test';
import {test as base} from '@playwright/test'
import { LoginPage } from '../pages/LoginPage';
import { SearchPage } from '../pages/SearchPage';
import { ProductPage } from '../pages/ProductPage';

type shop={
    login:LoginPage;
    search:SearchPage;
    product:ProductPage;
    cart:CartPage;
    // checkout:Checkout
}

export const test =base.extend<shop>({
    
    login:async({page},use)=>{
        await use(new LoginPage(page));
    },
    
    search: async ({ page }, use) => {
        await use(new SearchPage(page));
    },
    
    product: async ({ page }, use) => {
        await use(new ProductPage(page));
    },
    cart: async ({ page }, use) => {
        await use(new CartPage(page));
    },
    
});
export {expect} from '@playwright/test';