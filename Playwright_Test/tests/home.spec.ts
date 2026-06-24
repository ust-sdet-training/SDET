import { test, expect } from '@playwright/test';
import { HomePage } from '../pages/HomePage';

test.describe('Home Page Tests', () => {
  test('should navigate to the home page', async ({ page }) => {
    const homePage = new HomePage(page);
    await homePage.goto();
    await expect(page).toHaveURL('https://www.nykaa.com/');
    // await expect(page).toHaveTitle(/Nykaa/);
    await page.getByRole('link', { name: 'Nykaa Logo' }).click();
    await expect(page).toHaveURL('https://www.nykaa.com/?root=logo');
    // await expect(page).toHaveTitle("Nykaa");
    await page.locator('.image-wrapper.css-19k52lt > .css-13o7eu2').click();
    await expect(page).toHaveURL('https://www.nykaa.com/luxe/brands/sk-ii/c/98172?transaction_id=061587eb6457cffe7a1a854a09ac9157&intcmp=nykaa:hp:desktop-homepage:default:Brand_Gold_Takeover_Banner:CAROUSEL_V2:6:SK-II:98172:061587eb6457cffe7a1a854a09ac9157');
    
  });
  })


