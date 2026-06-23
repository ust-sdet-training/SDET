import {test} from '../fixtures/home';

test.describe(" HomePage",()=>{
    test("Homepage",async({home})=>{
       await home.searchProduct("Shirt");
    })
});