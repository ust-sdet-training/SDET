import { test ,expect} from '@playwright/test'
import { ShopFlow } from '../flows/Shopflows';

test.describe("Edge Cases Validation", () => {
  
  test('Invalid login' ,async({page})=>{
     await page.goto('/home');
     const s = new ShopFlow(page);
   await expect(s.validateHomePage());
   await s.sign_in();
   await s.invalid_login_in("invalid@example.com","secret@123");
  });

  test('Invalid Quantity', async({page})=>{
    await page.goto('/home');
     const s = new ShopFlow(page);
    await expect(s.validateHomePage());
    await s.sign_in();
    await s.login_in("customer@example.com","Password@123");
  });



});


