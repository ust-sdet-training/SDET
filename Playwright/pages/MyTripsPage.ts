
import {Page, expect} from "@playwright/test"
import { BasePage } from "./BasePage"
import { SearchData } from "../test-data/SearchData";
import { config } from "../utils/env";

export class MyTripsPage extends BasePage {

    constructor(page: Page) {
        super(page);
    }

    async open(){
        await this.page.goto(config.baseUrl + "my-trips");
    }
    async verifyBooking(pnr: string) {

        await expect(
            this.page.locator(`[data-id="trip-${pnr}"]`)
        ).toBeVisible();
    }
}