import { Locator, Page, expect } from "@playwright/test";

export class Header {

    constructor(readonly page: Page) { }

    private logInLink = () : Locator => this.page.getByRole('link', { name: 'Log in' });
    private logOutLink = () : Locator => this.page.getByRole('link', { name: 'Log out' });
    private flightsLink = () : Locator => this.page.getByRole('link', { name: 'Flights' });
    
    async clickLogInLink() {
        await this.logInLink().click();
    }

    async verifyLogIn(){
        await expect(this.logOutLink()).toBeVisible();
    }

    async clickFlightsLink() {
        await this.flightsLink().click();
    }

}