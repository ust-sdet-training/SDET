import { expect, Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";

export class SearchResultPage extends BasePage{

    readonly pageTitle: Locator;

    constructor(page: Page){
        super(page);

        this.pageTitle = page.getByRole("heading", {level:1});
    }

    async verifySearchResult(){
        await expect(this.pageTitle).toContainText("Flights");
    }

    async bookFlight(index:number=0){
        await this.page.getByRole("button",{name:"Book"}).nth(index).click();
    }

}