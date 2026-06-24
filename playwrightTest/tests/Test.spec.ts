import { test } from "../fixture";
import { testProduct } from "../fixture/testProduct";



test.describe("Nykaaa test",()=>{
    
    test("Home Page Visibility check",async ({home})=>{
        await home.checkHomePageLoaded();
    })

    test("Search for Valid product",async({home})=>{
        await home.searchValidProduct(testProduct.validproduct.name);
    })

    test("Search for Invalid product",async({home})=>{
        await home.searchInValidProduct(testProduct.invalidproduct.name);
    })
})