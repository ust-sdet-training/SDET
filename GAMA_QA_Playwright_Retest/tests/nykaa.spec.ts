import {test, expect} from "../fixtures/fixture.ts";

test.describe("Nykaa Search GAMA Test", ()=>{
    test("Going to nykaa and searching valid product", async ({home})=>{
        await home.gotoHomePage();
        await home.searchvalidProduct("shampoo");
    })

    test("Going to nykaa and searching invalid product", async ({home})=>{
        await home.gotoHomePage();
        await home.searchinvalidProduct("jacuuzi");
    })
})