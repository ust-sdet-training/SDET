import { expect, Page } from "@playwright/test";
import { BasePage } from "../base/BasePage";
import { PassengerLocators } from "../locators/PassengerLocators";

export class PassengerPage extends BasePage {

    readonly locator: PassengerLocators;

    constructor(page: Page) {
        super(page);
        this.locator = new PassengerLocators(page);
    }

    /**
     * Verify Passenger page is loaded
     */
    async verifyPassengerPageLoaded() {

        await expect(this.locator.passengerForm).toBeVisible();

        await expect(this.locator.email).toBeVisible();

        await expect(this.locator.phone).toBeVisible();

    }

    /**
     * Enter passenger details
     */
    async enterPassengerDetails(
        seat: string,
        firstName: string,
        lastName: string,
        age: string,
        gender: string,
        email: string,
        phone: string
    ) {

        await this.locator.firstName(seat).fill(firstName);

        await this.locator.lastName(seat).fill(lastName);

        await this.locator.age(seat).fill(age);

        await this.locator.gender(seat).selectOption(gender);

        await this.locator.email.fill(email);

        await this.locator.phone.fill(phone);

    }

    /**
     * Click Continue to Payment
     */
    async continueToPayment() {

        await this.locator.continueButton.click();

        await this.page.waitForLoadState("networkidle");

    }

    /**
     * Complete passenger form
     */
    async completePassengerForm(
        seat: string,
        firstName: string,
        lastName: string,
        age: string,
        gender: string,
        email: string,
        phone: string
    ) {

        await this.verifyPassengerPageLoaded();

        await this.enterPassengerDetails(
            seat,
            firstName,
            lastName,
            age,
            gender,
            email,
            phone
        );

        await this.continueToPayment();

    }

    /**
     * Get selected seat
     */
    async getSelectedSeat(): Promise<string> {

        return (await this.locator.seatList.textContent())?.trim() ?? "";

    }

    /**
     * Get journey type
     */
    async getJourneyType(): Promise<string> {

        return (await this.locator.journeyType.textContent())?.trim() ?? "";

    }

    /**
     * Get inventory id
     */
    async getInventoryId(): Promise<string> {

        return (await this.locator.inventoryId.textContent())?.trim() ?? "";

    }

    /**
     * Verify seat badge
     */
    async verifySeatBadge(seat: string) {

        await expect(this.locator.seatBadge(seat)).toBeVisible();

    }

}