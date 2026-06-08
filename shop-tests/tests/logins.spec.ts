import {expect,test} from "@playwright/test";
import { LoginPage } from "../pages/LoginPage";
 
test("Login test",async({ page })=>{
   
    const loginPage=new LoginPage(page);
    loginPage.check();
    await loginPage.login('customer@example.com ','Password@123');
    await page.goto('/home');
});