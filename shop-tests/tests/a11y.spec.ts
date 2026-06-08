import {expect} from '@playwright/test';
import {test} from '../fixtures/test';
import {testUsers} from '../../fixtures/test-users';
import { ProductPage } from '../pages/ProductPage';
import AxeBuilder from '@axe-core/playwright';

test.describe('Week 1 Evaluation Gate 1 a11y accesssibility',()=>{
    

    test('the product page for accessibility violations agianst WCAG 2 A/AA',async({shop,page})=>{
    await shop.addProductSuccess('Running Shoes');
    const accesibilitySCanResults =  await new AxeBuilder({page})
    .withTags(['wcag2a','wcag2aa'])
    .analyze();
    await expect(accesibilitySCanResults.violations).toEqual([]);
    console.log(accesibilitySCanResults.violations); 
});

});