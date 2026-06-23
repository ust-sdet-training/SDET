import { test } from "../fixtures";

test.describe("Home Page and Search", () => {

    test("Verify Home Page", async ({ home }) => {
        await home.verifyHomePage();
    });

    test("Verify Valid Product Search", async ({ home }) => {
        await home.searchValidProduct();
    });

    test("Verify Invalid Product Search", async ({ home }) => {
        await home.searchInvalidProduct();
    });

});