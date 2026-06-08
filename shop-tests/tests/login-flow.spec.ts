import {expect,test} from "@playwright/test";
import { testUsers } from "../fixtures/test";
import { LoginPage } from "../pages/LoginPage";

test("Login Flow",async({ page })=>{
    const loginflow = new LoginPage(page);
    await loginflow.check();
    await loginflow.login("user@test.com","Secret123");
    await page.goto('/home');
});

