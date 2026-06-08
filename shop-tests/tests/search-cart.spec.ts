import { expect,test } from "@playwright/test";
import { SearchPage } from '../pages/SearchPage';

test("Test of Search",async({page})=>{
    const searchProduct=new SearchPage(page);
    await searchProduct.search();
    await expect.soft(page.getByTestId('cart-count')).toHaveCount(1);
    
})

