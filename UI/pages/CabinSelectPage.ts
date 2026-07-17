import {Page} from "@playwright/test"

export class CabinSelectPage{

    page : Page;

    constructor(page: Page){
        this.page = page
    }


    async getFirstAvailableSeat(){
            await this.page.locator('.seat.available').first().click();
    }

    async continue(){
        await this.page.getByRole('button', { name: /Continue/i }).click();
    }

}