import {test,expect} from '@playwright/test';
import { SearchPage } from '../pages/SearchPage';
import { CartPage } from '../pages/CartPage';
import { PlaceOrderPage } from '../pages/PlaceOrderPage';

test('Checkout',async({page})=>{
    const cartProduct=new CartPage(page);
    await cartProduct.cart();
    
    const place=new PlaceOrderPage(page);
    await place.placeOrder();
});