import {test as base} from '@playwright/test';

import { SearchPage } from '../pages/SearchPage';
import { ProductPage } from '../pages/ProductPage';
import { LoginPage } from '../pages/LoginPage';
import { CartPage } from '../pages/CartPage';
import { ShopFlow } from '../flows/ShopFlow';

export const searchTest = base.extend<{search:SearchPage}>({
    search:async ({page},use)=>{
        await use(new SearchPage(page));
    },
});

export const addProductTest = base.extend<{add:ProductPage}>({
    add:async ({page},use)=>{
        await use(new ProductPage(page));
    },
});

export const loginTest = base.extend<{login:LoginPage}>({
   login:async({page},use)=>{
    await use(new LoginPage(page));
   }
 });

 export const cartPageTest = base.extend<{cart:CartPage}>({
   cart:async({page},use)=>{
    await use(new CartPage(page));
   }
 });

export const test = base.extend<{shop:ShopFlow}>({
   shop:async({page},use)=>{
    await use(new ShopFlow(page));
   }
});

export {expect} from '@playwright/test';