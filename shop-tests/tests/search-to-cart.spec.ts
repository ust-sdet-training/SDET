import { expect, test } from "@playwright/test";
import { testUsers } from "../fixtures/test";
import { LoginPage } from "../pages/LoginPage";
import { SearchPage } from "../pages/SearchPage";
import { Cart } from "../pages/CartPage";


test.describe("Week 1 Gate", () => {
    test.beforeEach(async ({ page}) => {
        await page.goto("/");
    })
    test("search-to-cart", async ({ page }) => {
        await page.goto('/home');
        await page.getByRole("link", {name: "Sign in"}).click();
        await expect(page).toHaveURL('/login');
        const login = new LoginPage(page);
        await login.login(testUsers.customer.email,testUsers.customer.password);
        await expect(page).toHaveURL('/home');
        await expect(page.getByRole("heading", { name: `Welcome, ${testUsers.customer.displayName}` })).toBeVisible();
        // const navbar = page.getByRole("navigation", {name: 'Primary navigation'});
        // await navbar.getByRole("link", {name: "Products"}).click();
        await page.getByRole("link", {name: "Preview products"}).click();
        await expect(page.getByRole("heading", {name: 'Product Catalog'})).toBeVisible();
        await expect(page).toHaveURL('/catalog');
        const search = new SearchPage(page);
        await search.search("Running Shoes");
        const addtocart = new Cart(page);
        await addtocart.addToCart("Running Shoes");
        await expect(addtocart.result()).toBeVisible();
        await expect(addtocart.badge()).toHaveText('1');
        await page.getByRole("button", {name: "Proceed to checkout"}).click();
        await expect(page).toHaveURL('/checkout');
        await page.getByRole('button', {name: "Place order"}).click();
        await expect(page.getByRole("heading", {name: "Thank you for your order"}));
        const order = page.waitForResponse((order) => order.url().includes('/api/orders') && order.status() === 200);
        await page.getByRole("button", {name: "View orders"}).click();
        await order;
        await expect(page.getByRole("heading", {name: "Order Detail"}));        

    })
    test("search-to-cart Through Navbar", async ({ page }) => {
        await page.goto('/home');
        await page.getByRole("link", {name: "Sign in"}).click();
        await expect(page).toHaveURL('/login');
        const login = new LoginPage(page);
        await login.login(testUsers.customer.email,testUsers.customer.password);
        await expect(page).toHaveURL('/home');
        await expect(page.getByRole("heading", { name: `Welcome, ${testUsers.customer.displayName}` })).toBeVisible();
        const navbar = page.getByRole("navigation", {name: 'Primary navigation'});
        await navbar.getByRole("link", {name: "Products"}).click();
        // await page.getByRole("link", {name: "Preview products"}).click();
        await expect(page.getByRole("heading", {name: 'Product Catalog'})).toBeVisible();
        await expect(page).toHaveURL('/catalog');
        const search = new SearchPage(page);
        await search.search("Running Shoes");
        const addtocart = new Cart(page);
        await addtocart.addToCart("Running Shoes");
        await expect(addtocart.result()).toBeVisible();
        await expect(addtocart.badge()).toHaveText('1');
        await page.getByRole("button", {name: "Proceed to checkout"}).click();
        await expect(page).toHaveURL('/checkout');
        await page.getByRole('button', {name: "Place order"}).click();
        await expect(page.getByRole("heading", {name: "Thank you for your order"}));
        const order = page.waitForResponse((order) => order.url().includes('/api/orders') && order.status() === 200);
        await page.getByRole("button", {name: "View orders"}).click();
        await order;
        await expect(page.getByRole("heading", {name: "Order Detail"}));        

    })


});
