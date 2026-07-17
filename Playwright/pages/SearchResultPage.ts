import { expect, Page } from "@playwright/test";
import { BasePage } from "./BasePage";

export class SearchResultPage extends BasePage {

    constructor(page: Page) {
        super(page);
    }

    

    searchResults = () => this.page.getByLabel("Flight results");
    firstFlight = () => this.page.locator(".op-name").first();
    firstFlightName = () => this.page.locator(".op-name").first().toString();
        
    private firstBookButton = this.page
        .getByRole("button", { name: "Book" })
        .first();

    
    

    async openFirstResult(){
        await this.firstBookButton.click();
        
    }

    

}
