import {AxeBuilder} from '@axe-core/playwright';
import { expect, test } from '@playwright/test';

test("a11y",async ({page}) =>{
    const results =await new AxeBuilder({ page }).analyze();
     
        const seriousViolations =
            results.violations.filter(violation => violation.impact === 'critical' || violation.impact === 'serious');
     
        expect.soft(seriousViolations).toEqual([]);
})