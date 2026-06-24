import { expect, test } from '@playwright/test';
import { Page } from '@playwright/test';


export class HomePage {

    constructor(private readonly page: Page){
    }

    async goto(){
        await this.page.goto('https://www.nykaa.com/');
    }

    async navigateToHomePage() {
        await this.page.goto('https://www.nykaa.com/');
        await this.page.waitForLoadState('networkidle');
        await this.page.locator('div').filter({ hasText: 'Nykaa' }).nth(3).click()
        await this.page.locator('div').filter({ hasText: 'Nykaa' }).nth(2).click()
    }

}

