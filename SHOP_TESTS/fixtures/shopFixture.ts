import { Checkout } from './../pages/Checkout';
import { AddCart } from './../pages/AddCart';
import { Page } from '@playwright/test';
import { Search } from './../pages/Search';
import {test as base} from '@playwright/test'
import { LoginPage } from '../pages/LoginPage';
import { Order } from '../pages/Order';

type shopFixture={
    login:LoginPage;
    search:Search;
    addcart:AddCart;
    checkout:Checkout;
    order:Order
}

export const test =base.extend<shopFixture>({
   
     login:async({page},use)=>{
       await use(new LoginPage(page));
     },

    search: async ({ page }, use) => {
        await use(new Search(page));
    },

    addcart: async ({ page }, use) => {
        await use(new AddCart(page));
    },
    checkout: async ({ page }, use) => {
        await use(new Checkout(page));
    },
    order: async ({ page }, use) => {
        await use(new Order(page));
    }

});

