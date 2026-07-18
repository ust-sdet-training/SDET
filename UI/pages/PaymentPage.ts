// import { expect, Page } from "@playwright/test";
// import { BasePage } from "../base/BasePage";
// import { PaymentLocators } from "../locators/PaymentLocators";

// export class PaymentPage extends BasePage {

//     readonly locator: PaymentLocators;

//     constructor(page: Page) {
//         super(page);
//         this.locator = new PaymentLocators(page);
//     }

//     async verifyPaymentPageLoaded() {
//         await expect(this.locator.paymentForm).toBeVisible();
//         await expect(this.locator.bookingId).toBeVisible();
//     }

//     async enterCardDetails(
//         holder: string,
//         number: string,
//         expiry: string,
//         cvv: string
//     ) {

//         await this.locator.cardName.fill(holder);
//         await this.locator.cardNumber.fill(number);
//         await this.locator.cardExpiry.fill(expiry);
//         await this.locator.cardCvv.fill(cvv);
//     }

//     async clickPay() {
//         await this.locator.payButton.click();
//         await this.page.waitForLoadState("networkidle");
//     }

//     async completePayment(
//         holder: string,
//         number: string,
//         expiry: string,
//         cvv: string
//     ) {

//         await this.enterCardDetails(
//             holder,
//             number,
//             expiry,
//             cvv
//         );

//         await this.clickPay();
//     }

//     async getBookingId(): Promise<string> {
//         return (await this.locator.bookingId.textContent()) ?? "";
//     }

//     async getJourneyType(): Promise<string> {
//         return (await this.locator.journeyType.textContent()) ?? "";
//     }

//     async getSelectedSeat(): Promise<string> {
//         return (await this.locator.seatList.textContent()) ?? "";
//     }

//     async getAmount(): Promise<string> {
//         return (await this.locator.amount.textContent()) ?? "";
//     }

//     async verifyOfferStrip() {
//         await expect(this.locator.offerStrip).toBeVisible();
//         await expect(this.locator.offers.first()).toBeVisible();
//     }
//     async verifyPayButtonDisabled() {
//     await expect(this.locator.payButton).toBeDisabled();
// }
// }

import { expect, Page } from "@playwright/test";
import { BasePage } from "../base/BasePage";
import { PaymentLocators } from "../locators/PaymentLocators";

export class PaymentPage extends BasePage {

    readonly locator: PaymentLocators;

    constructor(page: Page) {
        super(page);
        this.locator = new PaymentLocators(page);
    }

    async verifyPaymentPageLoaded() {

        await expect(this.locator.paymentForm).toBeVisible();
        await expect(this.locator.bookingId).toBeVisible();

    }

    async enterCardDetails(
        holder: string,
        number: string,
        expiry: string,
        cvv: string
    ) {

        await this.locator.cardName.fill(holder);
        await this.locator.cardNumber.fill(number);
        await this.locator.cardExpiry.fill(expiry);
        await this.locator.cardCvv.fill(cvv);

    }

    async clickPay() {

    await Promise.all([
        this.page.waitForLoadState("networkidle"),
        this.locator.payButton.click()
    ]);

}

    async completePayment(
        holder: string,
        number: string,
        expiry: string,
        cvv: string
    ) {

        await this.verifyPaymentPageLoaded();

        await this.enterCardDetails(
            holder,
            number,
            expiry,
            cvv
        );

        await this.clickPay();

    }

    async getBookingId(): Promise<string> {
        return (await this.locator.bookingId.textContent())?.trim() ?? "";
    }

    async getJourneyType(): Promise<string> {
        return (await this.locator.journeyType.textContent())?.trim() ?? "";
    }

    async getSelectedSeat(): Promise<string> {
        return (await this.locator.seatList.textContent())?.trim() ?? "";
    }

    async getAmount(): Promise<string> {
        return (await this.locator.amount.textContent())?.trim() ?? "";
    }

    async verifyOfferStrip() {

        await expect(this.locator.offerStrip).toBeVisible();
        await expect(this.locator.offers.first()).toBeVisible();

    }

    async verifyPayButtonDisabled() {

        await expect(this.locator.payButton).toBeDisabled();

    }
   async completeExpiredPayment(
    holder: string,
    number: string,
    expiry: string,
    cvv: string
): Promise<"CONFIRMED" | "HOLD_EXPIRED" | "SEAT_UNAVAILABLE"> {

    await this.verifyPaymentPageLoaded();

    await this.enterCardDetails(
        holder,
        number,
        expiry,
        cvv
    );

    await Promise.all([
        this.page.waitForLoadState("networkidle"),
        this.locator.payButton.click()
    ]);

    // Wait a moment for navigation or DOM update
    await this.page.waitForTimeout(1000);

    // 1. Booking confirmed
    if (this.page.url().includes("/book/confirmation/")) {
        return "CONFIRMED";
    }

    // 2. Hold expired
    if (
        await this.page.getByText(/HOLD_EXPIRED/i).isVisible().catch(() => false)
    ) {
        return "HOLD_EXPIRED";
    }

    // 3. Seat unavailable
    if (
        await this.page.getByText(/Seat no longer available/i).isVisible().catch(() => false)
    ) {
        return "SEAT_UNAVAILABLE";
    }

    throw new Error(
        `Unknown payment result.\nCurrent URL: ${this.page.url()}`
    );
}
}