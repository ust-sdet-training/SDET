import {expect} from '@playwright/test';
import {test} from '../fixtures/test';
import {testUsers} from '../../fixtures/test-users';

test.describe('Week 1 Evaluation Gate 1 unhappy test',()=>{

    test('invlaid login',async({shop,page})=>{
        await shop.loginFailed(testUsers.invalid.email,testUsers.invalid.password);
    });

    test('lock user',async({shop,page})=>{
        await shop.lockeuser(testUsers.locked.email,testUsers.locked.password);
    })

    test('search product with name not found',async({shop,page})=>{
        await shop.searchProductFailed('Abc Shoes');
    })
});