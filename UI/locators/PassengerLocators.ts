import { Locator, Page } from "@playwright/test";

export class PassengerLocators {

    readonly passengerForm: Locator;

    readonly email: Locator;
    readonly phone: Locator;

    readonly continueButton: Locator;

    readonly journeyType: Locator;
    readonly inventoryId: Locator;
    readonly seatList: Locator;

    readonly travellerCard: Locator;

    constructor(private page: Page) {

        this.passengerForm =
            page.locator("[data-id='passenger-form']");

        this.travellerCard =
            page.locator("[data-id='passenger-row']");

        this.email =
            page.locator("#email");

        this.phone =
            page.locator("#phone");

        this.continueButton =
            page.locator("button.btn.btn-cta");

        this.journeyType =
            page.locator("[data-id='journey-type']");

        this.inventoryId =
            page.locator("[data-id='inventory-id']");

        this.seatList =
            page.locator("[data-id='seat-list']");
    }

    /**
     * Dynamic locators based on selected seat
     * Example:
     * seat = "10D"
     * -> #name-10D
     * -> #lastname-10D
     * -> #age-10D
     * -> #gender-10D
     */

    firstName(seat: string): Locator {
        return this.page.locator(`#name-${seat}`);
    }

    lastName(seat: string): Locator {
        return this.page.locator(`#lastname-${seat}`);
    }

    age(seat: string): Locator {
        return this.page.locator(`#age-${seat}`);
    }

    gender(seat: string): Locator {
        return this.page.locator(`#gender-${seat}`);
    }

    seatBadge(seat: string): Locator {
        return this.page.locator(`text=Seat ${seat}`);
    }

}