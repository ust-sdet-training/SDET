import { Page } from "@playwright/test";
import { BasePage } from "../base/BasePage";
import { HomeLocators } from "../locators/HomeLocators";
import { DateHelper } from "../helpers/DateHelper";

export class HomePage extends BasePage {

    private readonly locators: HomeLocators;

    constructor(page: Page) {

        super(page);

        this.locators = new HomeLocators(page);

    }
    async searchFlight(
    from: string,
    to: string,
    days: number
) {

    // FROM
    await this.locators.fromTextBox.click();

    await this.fill(this.locators.fromTextBox, from);

    await this.locators.fromOption(from).waitFor({
        state: "visible"
    });

    await this.locators.fromOption(from).click();

    // TO
    await this.locators.toTextBox.click();

    await this.fill(this.locators.toTextBox, to);

    await this.locators.toOption(to).waitFor({
        state: "visible"
    });

    await this.locators.toOption(to).click();

    // DATE
    const date = DateHelper
        .getFutureDate(days)
        .toISOString()
        .split("T")[0];

    await this.locators.datePicker.fill(date);

    // SEARCH
    await this.click(this.locators.searchButton);

    await this.waitForPage();

}

    
}