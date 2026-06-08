
import { test, expect } from "@playwright/test"
import AxeBuilder from "@axe-core/playwright";
import { LoginPage } from "../pages/LoginPage";
import { SearchPage } from "../pages/SearchPage";

test.describe("Assessment 1", async() => {
    test("Login axe check", async ({page}) => {
        const loginPage = new LoginPage(page);
        await loginPage.goto();
        const result = await new AxeBuilder({page}).analyze();
        expect(result.violations.filter(
            v => v.impact === 'serious' || v.impact === 'critical')
        ).toEqual([]);
    })

    test("Catalog axe check", async({page}) => {
        const searchPage = new SearchPage(page);
        await searchPage.goto();
        const result = await new AxeBuilder({page}).analyze();
        expect(result.violations.filter(
            v => v.impact === 'serious' || v.impact === 'critical')
        ).toEqual([]);
    })

    test("Product axe check", async({page}) => {
        await page.goto("/product/running-shoes")
        const result = await new AxeBuilder({page}).analyze();
        expect(result.violations.filter(
            v => v.impact === 'serious' || v.impact === 'critical')
        ).toEqual([]);
    })

    test("Cart axe check", async({page}) => {
        await page.goto("/cart")
        const result = await new AxeBuilder({page}).analyze();
        expect(result.violations.filter(
            v => v.impact === 'serious' || v.impact === 'critical')
        ).toEqual([]);
    })

    test("Checkout axe check", async({page}) => {
        await page.goto("/checkout")
        const result = await new AxeBuilder({page}).analyze();
        expect(result.violations.filter(
            v => v.impact === 'serious' || v.impact === 'critical')
        ).toEqual([]);
    })

    test("Order axe check", async({page}) => {
        await page.goto("/order")
        const result = await new AxeBuilder({page}).analyze();
        expect(result.violations.filter(
            v => v.impact === 'serious' || v.impact === 'critical')
        ).toEqual([]);
    })



})