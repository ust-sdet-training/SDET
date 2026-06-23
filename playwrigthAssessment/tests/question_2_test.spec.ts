import {test,expect} from '../fixtures/test';
import { HomePage } from '../pages/HomePage';


test.describe('Home Page and search ' , () => {

    test('test Home Page visible ', async ({home,page}) => {
        home.navigateToHomePage();
        await expect(page).toHaveURL('https://www.shoppersstop.com/');
        await home.verifyLogoAndHomePageVisble(); 
    });

      test('test search for item and verify results as positive path', async ({home,page}) => { 
          home.navigateToHomePage();
         await expect(page).toHaveURL('https://www.shoppersstop.com/');
        home.searchForItem('watches');
        // await expect(page.getByRole('link', { name: 'product card Casio Vintage' })).toBeVisible();
    });

});

 

 test('test search for item and verify results as negative path', async ({home,page}) => {
        home.navigateToHomePage();
         await expect(page).toHaveURL('https://www.shoppersstop.com/');
        home.searchForItem('xyz');
        // await expect(page).toHaveURL(/\/search\/result\?q=xyz/);
        // await expect(page.getByText('No Result Found')).toBeVisible();
    // `)
    });
