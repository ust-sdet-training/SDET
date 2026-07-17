import {Page, Locator} from '@playwright/test';

export class ConfirmationPage{

    constructor(private readonly page : Page){}

    confirmationBadge = () : Locator => this.page.locator(".badge.badge-ok");
    pnr = () : Locator => this.page.locator(".pnr");
    viewMyTrips = () : Locator => this.page.getByRole("button", {name: "View my trips"})

    async verifyBadge(){
        return this.confirmationBadge;
    }

    async getPnrNumber(){
        return this.pnr;
    }

    async viewHistoryTrips(){
        await this.viewMyTrips().click();
    }

}