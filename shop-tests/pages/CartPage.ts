import { Locator, Page } from '@playwright/test';

export class CartPage {
    readonly page: Page;
    readonly checkoutButton: Locator;

    constructor(page: Page) {
        this.page = page;

        this.checkoutButton =
            page.getByRole('button', {
                name: 'Proceed to checkout'
            });
    }

    async checkout(): Promise<void> {
        await this.checkoutButton.click();
    }

    async waitForCartUpdate() {
        await this.page.waitForResponse(
            res => res.url().includes("/cart") && 
            res.request().method() === "POST" &&
            [200, 201].includes(res.status())
        );
    }

    getCartCount(): Locator {
    return this.page.getByTestId('cart-count');
}
}