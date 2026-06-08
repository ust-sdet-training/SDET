import{test,expect} from '@playwright/test';
import { testUsers } from '../fixtures/testUsers';
import { LoginPage } from '../pages/LoginPage';

test.describe("Login", ()=>{
    test("Testing Login Page", async({page})=>{
            const loginPage = new LoginPage(page);
            await loginPage.open();
            await expect(page).toHaveURL(/\/login/);
            await loginPage.login(testUsers.customer.email, testUsers.customer.password);
            await expect(page.getByRole("heading",{name: "Welcome, Customer User"})).toBeVisible();
            await expect(page.getByRole("button",{name: "Sign out"})).toBeVisible();
        });

    test("Testing Login Page with Invaild Inputs", async({page})=>{
            const loginPage = new LoginPage(page);
            await loginPage.open();
            await expect(page).toHaveURL(/\/login/);
            await loginPage.login(testUsers.invalid.email, testUsers.invalid.password);
            await loginPage.expectError("Invalid credentials");
        });
        
        test("Testing with special cases", async({page})=>{
            const loginPage = new LoginPage(page);
            await loginPage.open();
            await expect(page).toHaveURL(/\/login/);
            await loginPage.login("q%h@jb", "_&*$!$");
            await loginPage.expectError("Invalid credentials");
        });
        
        test("Testing Login with locked Inputs ", async({page})=>{
            const loginPage = new LoginPage(page);
            await loginPage.open();
            await expect(page).toHaveURL(/\/login/);
            await loginPage.login(testUsers.locked.email, testUsers.locked.password);
            await loginPage.expectError("Account is locked");
    });

        test("LOCKED USER CANNOT ADD/ORDER PRODUCTS", async({page})=>{
            const loginPage = new LoginPage(page);
            await loginPage.open();
            await expect(page).toHaveURL(/\/login/);
            await loginPage.login(testUsers.locked.email, testUsers.locked.password);
            await loginPage.expectError("Account is locked");

            await page.goto("/catalog");
            await page.getByRole("link", {name:"View Running Shoes"}).click();
            await page.getByRole("button",{name: "Add to cart"}).click();
            await page.getByRole("button",{name: "Proceed to checkout"}).click();
            await page.getByRole("button",{name: "Place order"}).click();
            await expect.soft(page.getByRole("alert",{name:"Locked User cannot be able order"})).toBeVisible();
        });

});