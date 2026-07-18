import { Page } from '@playwright/test';

export class BusSearchPage {

    constructor(private page: Page) {}

    async selectBusTab() {
        await this.page.getByRole('tab', { name: 'Buses' }).click();
    }

    async selectFromCity(cityLabel: string) {
        await this.page.getByRole('combobox', { name: 'From' }).click();
        await this.page.getByRole('option', { name: cityLabel }).click();
    }

    async selectToCity(cityLabel: string) {
        await this.page.getByRole('combobox', { name: 'To' }).click();
        await this.page.getByRole('option', { name: cityLabel }).click();
    }

    async selectDate(date: string) {
        await this.page.getByRole('textbox', { name: 'Date' }).fill(date);
    }

    async searchBus() {
        await this.page.getByRole('button', { name: 'Search' }).click();
    }

    async openSeatSelection() {
        // Picks the first bus result regardless of operator (NueGo, VRL, etc.)
        // so this doesn't break if the first result on a given day differs.
        await this.page
            .getByRole('button', { name: 'Select Seats' })
            .first()
            .click();
    }
}