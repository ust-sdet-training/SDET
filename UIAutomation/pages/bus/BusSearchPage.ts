import { Page } from '@playwright/test';

export class BusSearchPage {

    constructor(
        private page: Page
    ) {}

    async selectBusTab() {

        await this.page
            .getByRole(
                'tab',
                { name: 'Buses' }
            )
            .click();
    }

    async selectFromCity(
        city: string
    ) {

        await this.page
            .getByRole(
                'combobox',
                { name: 'From' }
            )
            .click();

        await this.page
            .getByRole(
                'option',
                { name: city }
            )
            .click();
    }

    async selectToCity(
        city: string
    ) {

        await this.page
            .getByRole(
                'combobox',
                { name: 'To' }
            )
            .click();

        await this.page
            .getByRole(
                'option',
                { name: city }
            )
            .click();
    }

    async selectDate(
        date: string
    ) {

        await this.page
            .getByRole(
                'textbox',
                { name: 'Date' }
            )
            .fill(date);
    }

    async searchBus() {

        await this.page
            .getByRole(
                'button',
                { name: 'Search' }
            )
            .click();
    }

    async openSeatSelection() {

        await this.page
            .getByRole(
                'button',
                {
                    name: 'Select Seats'
                }
            )
            .first()
            .click();
    }
}