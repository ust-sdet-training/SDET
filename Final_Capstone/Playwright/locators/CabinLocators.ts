import { Page, Locator } from "@playwright/test";

export class CabinLocators{
    readonly continue: Locator
    readonly seat : Locator

    constructor(page: Page){
        this.continue = page.locator("#continue-btn");
        this.seat = this.seat = page.locator(".seat.available").first()
    }
}