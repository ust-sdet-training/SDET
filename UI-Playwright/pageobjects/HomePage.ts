import { Locator, Page, expect } from "@playwright/test";

export class HomePage {

    constructor(readonly page: Page) { }

    private tripStackHeader = () : Locator => this.page.getByRole('heading',{name:'Book flights & buses across India'});
    
    async open(){
        await this.page.goto('/');
    }

    async verifyHomePageLoaded() {
        await expect(this.tripStackHeader()).toBeVisible()
    }

}