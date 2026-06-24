import {test} from '../fixtures/home';
import {products} from '../fixtures/testproducts';
test.describe(" HomePage",()=>{
    test("Homepage",async({home})=>{
       await home.searchProduct(products.productdetails.name);
    })
});