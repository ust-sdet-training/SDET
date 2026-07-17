import { expect, Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";
import { logger } from "../utils/Logger";

export class SearchResultPage extends BasePage{

    readonly pageTitle: Locator;

    constructor(page: Page){
        super(page);

        this.pageTitle = page.getByRole("heading", {level:1});
    }

    async verifySearchResult(){
        logger.info("[SearchResultPage] Verifying search results");
        await expect(this.pageTitle).toContainText("Flights");
    }

    async bookFlight(index:number=0){
        logger.info(`[SearchResultPage] Booking flight at index ${index}`);
        await this.page.getByRole("button",{name:"Book"}).nth(index).click();
    }

}