import AxeBuilder from '@axe-core/playwright';
import {test, expect} from '@playwright/test';

// test.describe("Week 1 Day 4: Accessibility Works Tests", ()=>{
//     test(" 1. Acesssiblity rules with analyze for violations: ", async ({page})=>{
//         for (const path of ['/catlaog', '/product/running`-shoes', '/cart']){
//             await page.goto(path);
//             const scanResuls=await new AxeBuilder({page})
//             .disableRules('color-contrast').analyze();
//             expect(scanResuls.violations).toEqual([]);
//         }
//     })

//     test(" 2. Acesibility for wcag2a & wcag2aa", async ({page})=>{
//         await page.goto('/');
//         const primary_nav= page.getByRole('navigation', {name: "Primary navigation"});
//         await primary_nav.getByRole("link", {name: 'Products'}).click();
//         await expect(page).toHaveURL(/\/catalog$/);
        
//         const scanResult=await new AxeBuilder({page})
//         .withTags(["wcag2a", "wcag2aa"]).analyze();
//         expect(scanResult.violations).toEqual([]);
//     })

//     test(" 3. Acesibility with Violations in Page", async ({page})=>{
//         await page.goto('/');
//         const primary_nav=page.getByRole("navigation", {name: "Primary navigation"});
//         await primary_nav.getByRole("link", {name: 'A11y Lab'}).click();

//         await expect(page).toHaveURL(/\/a11y-lab$/);
//         await expect(page.getByRole('heading', {name: 'A11y Lab'})).toBeVisible();

//         const scanResult = await new AxeBuilder({page})
//                         .withTags(["wcag2a", "wcag2aa"]).analyze();
//         scanResult.violations.length>0 ?
//         console.log(
//             scanResult.violations[0].id, scanResult.violations[0].description
//         )
//         : console.log("No violation");
//         expect(scanResult.violations).toEqual([]);
//     })
// })

