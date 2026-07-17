import { Page } from '@playwright/test';

export class BasePage {

    protected page: Page;

    constructor(page: Page) {
        this.page = page;
    }

    async navigate(url: string) {
        await this.page.goto(url);
    }

    async waitForPage() {
        await this.page.waitForLoadState('networkidle');
    }
}