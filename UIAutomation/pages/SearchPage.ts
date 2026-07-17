import { Page } from '@playwright/test';

export class SearchPage {

    constructor(
        private page: Page
    ) {}

    async searchBus(
        from: string,
        to: string,
        date: string
    ) {

        await this.page
            .getByRole(
                'tab',
                {
                    name: 'Buses'
                }
            )
            .click();

        await this.page
            .getByRole(
                'combobox',
                {
                    name: 'From'
                }
            )
            .click();

        await this.page
            .getByRole(
                'option',
                {
                    name: from
                }
            )
            .click();

        await this.page
            .getByRole(
                'combobox',
                {
                    name: 'To'
                }
            )
            .click();

        await this.page
            .getByRole(
                'option',
                {
                    name: to
                }
            )
            .click();

        await this.page
            .getByRole(
                'textbox',
                {
                    name: 'Date'
                }
            )
            .fill(date);

        await this.page
            .getByRole(
                'button',
                {
                    name: 'Search'
                }
            )
            .click();
    }
}