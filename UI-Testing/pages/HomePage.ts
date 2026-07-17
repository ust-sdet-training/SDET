import { expect, Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";
import { AppLogger } from "../utils/Logger";

export class HomePage extends BasePage {
    readonly loginBtn: Locator
    readonly homeText: Locator
    readonly fromBox: Locator
    readonly toBox: Locator
    readonly dateBox: Locator
    readonly searchBtn: Locator

    constructor(page: Page, log: AppLogger) {
        super(page, log);

        this.loginBtn = page.locator("a", { hasText: "Log in" });
        this.homeText = page.getByText("Book flights & buses across India")
        this.fromBox = page.getByRole("combobox", { name: "From" })
        this.toBox = page.getByRole("combobox", { name: "To" })
        this.dateBox = page.getByLabel("Date")
        this.searchBtn = page.getByRole("button", {name: "Search"})
    }

    async gotoLoginPage(): Promise<void> {
        this.log.info("Opening Home Page")
        await this.open("/")
        await expect(this.isVisible(this.homeText))
        await expect(this.isVisible(this.loginBtn))
        this.log.info("Opening Login Page")
        await this.click(this.loginBtn)
    }

    async searchFlight(from: string, to: string, date: string): Promise<void> {
        this.log.info("Searching for Flight")
        await this.fill(this.fromBox, from)
        await this.fill(this.toBox, to)
        await this.fill(this.dateBox, date)
        await this.click(this.searchBtn)
    }
}