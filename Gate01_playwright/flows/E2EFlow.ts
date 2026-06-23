import {expect, Page} from '@playwright/test';
import { dashboard } from '../pages/dashboard';
export class E2EFlow {
    readonly Dashboard: dashboard;

    constructor(private readonly page: Page) {
        this.Dashboard = new dashboard(page);
    }

    async search_product(itemName: string): Promise<void> {
        await this.Dashboard.Search(itemName);
    }

    async navigate_to_productPage(itemName: string): Promise<void> {
        await this.Dashboard.NavigateToProductPage(itemName);
    }
} 