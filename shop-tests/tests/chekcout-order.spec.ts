import {expect,test} from '@playwright/test';
import { CartPage } from '../pages/CartPage';
import { CheckoutPage } from '../pages/CheckoutPage';

test("chekcout to order",async ({ page })=>{
    const checkoutToOrder = new CartPage(page);
    await checkoutToOrder.cartToCheckout();
    const order = new CheckoutPage(page);
    await order.checkoutToOrders();

})