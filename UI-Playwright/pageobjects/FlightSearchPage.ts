import { Locator, Page, expect } from "@playwright/test";

export class FlightSearchPage {

    constructor(readonly page: Page) { }

    private fromInputField = () : Locator => this.page.getByRole('combobox', { name: 'From' });
    private toInputField = () : Locator => this.page.getByRole('combobox', { name: 'To' });
    private inputFieldOption = (option: string) : Locator => this.page.getByRole('option', { name: `${option}` });
    private dateInputField = (date: string) : Locator => this.page.locator(`//button[@data-date='${date}']`);
    private searchButton = () : Locator => this.page.getByRole('button', { name: 'Search' });

    async verifyFlightSearchPageLoaded(){
        await expect(this.page.url()).toContain('/flights/search');
        await expect(this.fromInputField()).toBeVisible();
    }

    async searchFlight(from: string,fromOption: string, to: string, toOption: string, date: string) {
        await this.fromInputField().fill(from);
        await this.inputFieldOption(fromOption).click();
        await this.toInputField().fill(to);
        await this.inputFieldOption(toOption).click();
        await this.dateInputField(date).click();
        await this.searchButton().click();
    }

}