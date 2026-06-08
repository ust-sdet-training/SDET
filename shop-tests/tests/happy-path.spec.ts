import { test } from "../fixtures/index";
import { testProduct } from "../fixtures/testProduct";
import { testUsers } from "../fixtures/testUsers";

test.describe('Happy path',()=>{
    test('Fully sucess path from login to validate flow',async ({shop,login})=>{
        await login.ValidLogin(testUsers.validCustomer.email,testUsers.validCustomer.password);
        await shop.searchExistingProduct(testProduct.validProduct.name);
        await shop.addFirstResultToCart();
        await shop.cartCheckout();
        await shop.placeOrder();
        await shop.validateOrder();
    })

})