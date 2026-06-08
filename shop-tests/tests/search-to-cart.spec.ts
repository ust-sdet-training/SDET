import { test } from "../fixtures/shopFixture";
import { testProduct } from "../fixtures/testProduct";

test.describe('Search page function and adding to cart functions',()=>{
   
    test('Search Exisiting Product',async ({shop})=>{
        await shop.searchExistingProduct(testProduct.validProduct.name);
    })

    test('view Existing product',async ({shop})=>{
        await shop.searchExistingProduct(testProduct.validProduct.name);
    })

    test('Search and add to cart with existing product',async ({shop})=>{
       await shop.searchExistingProduct(testProduct.validProduct.name);
        await shop.addFirstResultToCart();
    })
    
})