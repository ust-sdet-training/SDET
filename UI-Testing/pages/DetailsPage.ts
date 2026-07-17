import { expect, Locator, Page } from "@playwright/test";
import { BasePage } from "./BasePage";
import { AppLogger } from "../utils/Logger";
import { Passenger } from "../model/Passenger";

export class DetailsPage extends BasePage {
    readonly whoText: Locator
    readonly seatBadge: Locator
    readonly email: Locator
    readonly phone: Locator
    readonly continueBtn: Locator

    constructor(page: Page, log: AppLogger) {
        super(page, log);

        this.whoText = page.getByText("Who's travelling?")
        this.seatBadge = page.locator(".badge");
        this.email = page.getByLabel("Email");
        this.phone = page.getByLabel("Phone number");
        this.continueBtn = page.getByRole("button", {name: "Continue to payment"});
    }

    async verifyDetailsPage(): Promise<void> {
        await expect(this.isVisible(this.whoText))
    }

    async fillPassengerDetails(passenger: Passenger): Promise<void> {
        this.log.info("Filling passenger details");
        const seat = (await this.seatBadge.textContent())!.replace("Seat", "").trim();
        await this.fill(this.page.locator(`#name-${seat}`), passenger.firstName)

        await this.fill(this.page.locator(`#lastname-${seat}`), passenger.lastName)

        await this.fill(this.page.locator(`#age-${seat}`), passenger.age.toString())

        await this.page.locator(`#gender-${seat}`).selectOption(passenger.gender);

        await this.fill(this.email, passenger.email)

        await this.email.fill(passenger.email);

        await this.phone.fill(passenger.phone);
    }

    async continueToPayment(): Promise<void> {
        this.log.info("Proceeding to payment");
        await this.click(this.continueBtn);
    }
}