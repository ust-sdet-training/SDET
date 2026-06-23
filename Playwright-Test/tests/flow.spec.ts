import { expect, test } from "@playwright/test";
import { HomePage } from "../pages/HomePage";

test.describe("Flow", async() => {
    test("Flow test", async({page}) => {
        const homePage = new HomePage(page);
        await homePage.goto();
        await homePage.search("shirt");

    })
})