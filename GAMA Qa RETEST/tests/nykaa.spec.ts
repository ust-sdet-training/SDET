
import {test} from "../fixtures/index"


test("Search for a product that is  in Nykaa Website",async ({stest})=>{
    await stest.ptest("Shampoo");
})

test("Search for a product that is not in  Nykaa Website",async ({stest})=>{
    await stest.ntest("abcefg");
})
