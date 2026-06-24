import {test, expect} from "@playwright/test"
import { HomePage } from "../pages/HomePage";
import { SearchFlow } from "../flows/SearchFlow";

test.describe("Gate 1 Assessment", async() => {
    test("Search Flow", async({page}) => {
        const homePage = new HomePage(page);

        await SearchFlow(page, "shirt");
        await expect(homePage.productList()).toBeVisible();
        await homePage.firstProduct();
    })

    test("Negative Path: Search for invalid item", async({page}) => {
        const homePage = new HomePage(page);
        await homePage.goto();
        await SearchFlow(page, "abc123asd");

        await expect(page.getByText('Unfortunately')).toBeVisible();
    })
})