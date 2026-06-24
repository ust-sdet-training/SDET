import {test} from "../fixtures/test";

test.describe("Purchase Tests", () =>{

    test("Search for existing product", async({shop}) =>{
        await shop.search_existing_product("hand sanitiser")
    });

    test("Search for non-existing product", async({shop}) =>{
        await shop.search_non_existing_product("%&*^&^")
    });
});