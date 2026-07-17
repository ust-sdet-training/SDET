import {Page} from "@playwright/test"

export class FlightHomePage{

    page : Page;

    constructor(page: Page){
        this.page = page
    }

    async goto(){
        await this.page.goto('/')
    }

    async setDepatureLocation(location: string){

        await this.page.getByRole('combobox', { name: 'From' }).click();
        await this.page.getByRole('combobox', { name: 'From' }).fill(location);

        await this.page.getByRole('option', { name: new RegExp(location) }).click();

    
    }

    async setDestinationLocation(location: string){


        await this.page.getByRole('combobox', { name: 'To' }).click();
        await this.page.getByRole('combobox', { name: 'To' }).fill(location);
        await this.page.getByRole('option', { name: new RegExp(location)}).click();
    
    }

    async setDate(difference: number){

        const date = new Date();
        date.setDate(date.getDate() + difference);
        const plus14Days = date.toISOString().split('T')[0];
        await this.page.getByRole('textbox', { name: 'Date' }).fill(plus14Days);

        return plus14Days

    }

    async setCabin(cabintype: string){

        await this.page.locator('select[name="cls"]').selectOption({ label: cabintype })
        

    }

    async search(){

        await this.page.getByRole('button', { name: 'Search' }).click(); 

    }

}