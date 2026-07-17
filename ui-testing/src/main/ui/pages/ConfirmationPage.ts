import { expect, Page } from "@playwright/test";

export class ConfirmationPage{
    constructor(private readonly page: Page) {}  
    async goto() {
        await this.page.goto('/');
    }

    async confirmSeat(): Promise<void> {
        await expect(this.page.getByText('TS-1002-')).toBeVisible();
        await expect(this.page.locator('[data-id="state"]')).toHaveText("CONFIRMED");
    }
}