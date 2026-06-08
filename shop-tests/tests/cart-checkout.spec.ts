import {expect,test} from '@playwright/test';
import { SearchPage } from '../pages/SearchPage';

test("cart-chekcout",async({ page })=>{
    const cartCheckout = new SearchPage(page);
    await cartCheckout.searchToCart();

});