import { test, expect} from "@playwright/test";
import { Users } from "../fixtures/test";
import { LoginPage } from "../pages/login";
import { searchPage } from "../pages/search";
import { Cart } from "../pages/cart";



test.describe("Week 1 Gate", () => {
    test.beforeEach(async ({ page}) => {
        await page.goto("/");
    })
    test("test 01  Login", async ({ page }) => {
        await page.goto("/home");
        await page.getByRole("link", {name: "Sign in"}).click();
        await expect(page).toHaveURL("/login");
        const login = new LoginPage(page);
        await login.login(Users.customer.email, Users.customer.password);
        await expect(page).toHaveURL("/home");
        await expect(page.getByRole("heading", {name: `Welcome, ${Users.customer.displayName}`})).toBeVisible();
        await expect(page.getByRole("button", {name: "Sign out"})).toBeVisible();
        // const productwait = page.waitForResponse((productwait) => productwait.url().includes('api/products') && productwait.status() === 304);
        await page.getByRole("link", {name: "Preview products"}).click();
        // await productwait;
        await expect(page.getByRole("heading", {name: "Product Catalog"})).toBeVisible();
        await expect(page).toHaveURL("/catalog");
        const search = new searchPage(page);
        await search.search("Running Shoes");
        const addtocart = new Cart(page);
        await addtocart.addToCart("Running Shoes");
        await expect(addtocart.result()).toBeVisible();
        await expect(addtocart.badge()).toHaveText('1');
        await page.getByRole("button", {name: "Proceed to checkout"}).click();
        await expect(page).toHaveURL('/checkout');
        await expect(page.getByRole("heading", {name: "Checkout", exact: true})).toBeVisible();
        await page.getByRole("button", {name: "Place order"}).click();
        await expect(page.getByText("Order created")).toBeVisible();
        // await expect(page.getByText("Order   is confirmed.")).toBeVisible();
        const order = page.waitForResponse((order) => order.url().includes('/api/orders') && order.status() === 200);
        await page.getByRole("button", {name: "View order"}).click();
        await order;
        await expect(page.getByRole("heading", {name: "Orders"})).toBeVisible();
        await expect(page).toHaveURL("/orders");
        await expect(page.getByRole("table", {name: "Recent retail orders" })).toBeVisible();



    });

});
