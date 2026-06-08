import {expect} from '@playwright/test';
import {test} from '../fixtures/test';
import {testUsers} from '../../fixtures/test-users';
import { ProductPage } from '../pages/ProductPage';

test.describe('Week 1 Evaluation Gate 1 happy test',()=>{



    test('happy search product',async({shop,page})=>{
        
        await shop.loginSuccess(testUsers.customer.email,testUsers.customer.password);
        await expect.soft(page).toHaveURL('/home');
    });

    test('lock user',async({shop,page})=>{
        await shop.lockeuser(testUsers.locked.email,testUsers.locked.password);
        await expect.soft(page).toHaveURL('/login');
    })

    test('search product with name',async({shop,page})=>{
        await shop.searchProductSuccess('Running Shoes');
        await expect.soft(page).toHaveURL('/catalog');
    })

    test('add product success',async({shop,page})=>{
        await shop.addProductSuccess('Running Shoes');
        await expect.soft(page).toHaveURL('/cart');
    })


    test('total price of cart and count',async({shop,page})=>{
        await shop.cartProductQuantity("Running Shoes");
        await expect.soft(page).toHaveURL('/cart');
    })

     test.only('checkout and validate',async({shop,page})=>{
        await shop.checkoutAndValidate("Running Shoes");
    })


});