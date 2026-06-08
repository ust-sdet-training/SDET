import { Locator, Page } from '@playwright/test';

export class CheckoutPage {
    readonly page: Page;
    readonly placeOrderButton: Locator;

    constructor(page: Page) {
        this.page = page;

        this.placeOrderButton =
            page.getByRole('button', {
                name: 'Place Order'
            });
    }

    async placeOrder(): Promise<void> {
        await this.placeOrderButton.click();
    }
}