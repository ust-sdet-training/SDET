import {test,expect} from '@playwright/test'
import AxeBuilder from '@axe-core/playwright'

test('Accessibility test' ,async({page})=>{

     await page.goto('/home');

        const PrimaryNavigation = page.getByRole('navigation',{name:"Primary navigation"});
        await PrimaryNavigation.getByRole('link',{name:"A11y Lab"}).click();
   

        const accessibity = await new AxeBuilder({page}).analyze();

        expect(accessibity.violations).toEqual([]);

})