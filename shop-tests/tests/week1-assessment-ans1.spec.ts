import { test ,expect} from '@playwright/test'
import { ShopFlow } from '../flows/ShopFlow';
 
test('Week 1 Capstone ', async ({ page }) => {
   
   await page.goto('/home');
   const entireFlow = new ShopFlow(page);
   await expect(entireFlow.validateHomePage());
   await entireFlow.signIn();
   await entireFlow.loginIn("customer@example.com","Password@123");
   await entireFlow.goToCatalog("Products");
   await entireFlow.search("Running");
   await entireFlow.addToCart();
   await entireFlow.proceedToCheckout();
   await entireFlow.placeOrder();
});