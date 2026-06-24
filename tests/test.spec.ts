import {test} from "../fixtures";

test.describe("Home Page to Search with Valid and Invalid Products", () => {
    test("Home Page Verifying", async ({homeFlow}) => {
        await homeFlow.verifyPage();
    });

    test("Search with vaild Product", async ({homeFlow}) => {
        await homeFlow.searchValidProduct();
    });

    test("Search with invaild Product", async ({homeFlow}) => {
        await homeFlow.searchInvaildProduct();
    });
});