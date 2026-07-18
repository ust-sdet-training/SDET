import { expect, type Page } from "@playwright/test";
import { Logger } from "../src/utils/Logger";

export class SearchPage {

    constructor(private page: Page) {}

    async goto() {

        Logger.info("Opening TripStack Website");

        await this.page.goto("https://tripstack.doomple.com/");

        Logger.success("Website Opened");
    }

    async search() {

        Logger.info("Selecting Bus Tab");
        await this.page.getByRole('tab', { name: 'Buses' }).click();

        Logger.info("Selecting Source City");
        await this.page.getByRole('combobox', { name: 'From' }).click();
        await this.page.getByRole('option', { name: 'Delhi DEL' }).click();

        Logger.success("Source Selected : Delhi");

        Logger.info("Selecting Destination City");
        await this.page.getByRole('combobox', { name: 'To' }).click();

        await expect(
                this.page.getByRole('combobox', { name: 'To' })
        ).toBeEmpty();

        await this.page.getByRole('combobox', { name: 'To' }).click();

        await this.page.getByRole('option', {
            name: 'Chandigarh IXC'
        }).click();

        Logger.success("Destination Selected : Chandigarh");

        Logger.info("Clicking Search Button");

        await this.page.getByRole('button', {
            name: 'Search'
        }).click();

        Logger.success("Bus Search Completed Successfully");
    }

    async errorMessage() {
        return this.page.locator("#error");
    }

}