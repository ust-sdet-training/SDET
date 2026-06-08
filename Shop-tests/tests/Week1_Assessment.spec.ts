import { test ,expect} from '@playwright/test'
import { ShopFlow } from '../flows/Shopflows';

test('Week 1 Assessment ', async ({ page }) => {
   

   await page.goto('/home');

   const s = new ShopFlow(page);
   await expect(s.validateHomePage());
   await s.sign_in();
   await s.login_in("customer@example.com","Password@123");
   await s.goToCatalog("Products");
   
   await s.search("Running");

   await s.addToCart();

   await s.proceed_to_checkout();

   await s.place_order();
});

