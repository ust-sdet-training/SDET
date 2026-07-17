import { Locator, Page, expect } from "@playwright/test";

export class FlightSearchResultsPage {

    constructor(readonly page: Page) { }

    private sortByOption = (option: string) : Locator => this.page.getByRole('button', { name: `${option}` });
    private bookButton = (flightName: string) : Locator => this.page.getByLabel(`${flightName}`).getByRole('button', { name: 'Book' });
    private wrongSearchHeader = () : Locator => this.page.getByRole('heading',{name:'No flights found'});

    async verifyFlightSearchResultsPageLoaded(){
        await expect(this.page.url()).toContain('/flights/results');
        await expect(this.sortByOption('Departure')).toBeVisible();
    }

    async sortBy(option: string) {
        await this.sortByOption(option).click();
    }

    async bookFlight(flightName: string) {
        await this.bookButton(flightName).click();
    }

    async invalidRouteSearchResult(){
        await expect(this.wrongSearchHeader()).toBeVisible();
    }

}