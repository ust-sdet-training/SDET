import { test, expect } from "../fixtures/app.fixture";
import { HomePage } from "../pages/HomePage";
import { SearchPage } from "../pages/SearchPage";
import { ProductPage } from "../pages/ProductPage";
import { CartPage } from "../pages/CartPage";
import { CheckoutPage } from "../pages/CheckoutPage";
import { OrderPage } from "../pages/OrderPage";

test("login to cart flow for logging and diagnostics", async ({ page, log }) => {
        const homePage = new HomePage(page);
        const searchPage = new SearchPage(page);
        const productPage = new ProductPage(page);
        const cartPage = new CartPage(page);
        const checkoutPage = new CheckoutPage(page);
        const orderPage = new OrderPage(page);

        await page.goto("/catalog");
        log.info("Open catalog page");

        await homePage.productsPage();

        log.info("Searching for keyword")
        await searchPage.search('run');
        await expect(searchPage.resultCount()).toContainText(' 1 ');
        await expect(searchPage.cardCount()).toHaveCount(1);


        
        await searchPage.cardCount().first().getByRole('link', {name: /view/i}).click();
        log.info("Click first result")
        await expect(page).toHaveURL(/\/running-shoes/);
        await productPage.add_cart();

        await expect(page).toHaveURL(/\/cart/);
        await expect(cartPage.cartCount()).toHaveText('1');
        await cartPage.checkout();
        log.info("Move to checkout")
        await expect(page).toHaveURL(/\/checkout/);

        await checkoutPage.order();
        await page.getByRole("button", {name: "View orders"}).click();
        log.info("Placing an order")

        const orderNo = await CheckoutPage.orderNo as unknown as string;
        await orderPage.checkForOrder(orderNo);
        log.info("Order number is shown in orders")

    })