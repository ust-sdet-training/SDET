
import {test,expect} from "@playwright/test";
import { HomePage } from "../pages/HomePage";


test.only("Home page check", async ({ page }) => {
  const home=new HomePage(page);
        await home.home();
  
});



