import AxeBuilder from "@axe-core/playwright";
import {test, expect } from "@playwright/test";

test.describe('Accesibility checks',()=>{
    test('çhecking the accesibility of home ,Catelog , a11y-lab pages',async ({page})=>{
        for(const path of ["/catelog","/a11y-lab","/"]){
            await page.goto(path);
            const res = await new AxeBuilder({page}).analyze();
            expect(res.violations).toEqual([]);
        }
    })
    
    test('checking all the home with crital or serious accebility issues',async ({page})=>{
        await page.goto('/');
        const res = await new AxeBuilder({page}).analyze();
        expect(res.violations.filter(v=> v.impact === 'critical' || v.impact==='serious')).toEqual([]);       
    })

})