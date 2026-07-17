import {Page} from "@playwright/test"

export class FlightListPage{

    page : Page;

    constructor(page: Page){
        this.page = page
    }

    async bookFirstFlight(){
            await this.page.getByRole('button', { name: 'Book' }).first().click();
    }

}