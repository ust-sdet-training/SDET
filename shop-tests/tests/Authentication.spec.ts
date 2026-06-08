import { test,expect } from '@playwright/test';
import {HomePage} from '../pages/HomePage';
import {LoginPage} from '../pages/LoginPage';
import {creds} from '../test-data/loginCredentials';

test.describe('Authentication Tests',()=>{

    test('Valid Login Test',async({page})=>{
         const homePage = new HomePage(page);
            await homePage.goToHomePage();
            expect(await homePage.getTitleHeading()).toBe('SDET Retail Automation Lab');
            await homePage.clickOnSignInButton();
        
            const loginPage = new LoginPage(page);
            await loginPage.login(creds.customer.email,creds.customer.password,creds.customer.country);
        
            await expect(page).toHaveURL(/home/);
            expect(await homePage.getTitleHeading()).toBe('Welcome, Customer User');
    })

    test('Invalid Login Test',async({page})=>{
         const homePage = new HomePage(page);
            await homePage.goToHomePage();
            expect(await homePage.getTitleHeading()).toBe('SDET Retail Automation Lab');
            await homePage.clickOnSignInButton();
        
            const loginPage = new LoginPage(page);
            await loginPage.login(creds.customer.email,creds.invalid.password,creds.invalid.country);
        
            expect(await loginPage.getErrorMessage()).toBe(creds.invalid.error_message);
    })

    test('Locked User Login Test',async({page})=>{
         const homePage = new HomePage(page);
            await homePage.goToHomePage();
            expect(await homePage.getTitleHeading()).toBe('SDET Retail Automation Lab');
            await homePage.clickOnSignInButton();
        
            const loginPage = new LoginPage(page);
            await loginPage.login(creds.locked.email,creds.locked.password,creds.locked.country);
        
            expect(await loginPage.getErrorMessage()).toBe(creds.locked.error_message);
    })

    test('Clicking Sign In Without Entering Credentials',async({page})=>{
        const homePage = new HomePage(page);
            await homePage.goToHomePage();
            expect(await homePage.getTitleHeading()).toBe('SDET Retail Automation Lab');
            await homePage.clickOnSignInButton();
        
            const loginPage = new LoginPage(page);
            await loginPage.clickOnSignInButton();
            await expect(page).toHaveURL(/login/);
    })
    

})