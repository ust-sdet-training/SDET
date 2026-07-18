import { expect, Page } from "@playwright/test";

/**
* This page contains all the locators and all the actions for booking confirmation.
*/
export class ConfirmationPage {
    private readonly pnrLocator;
    private readonly paymentDeclineBannerLocator;

    constructor(private readonly page:Page) {
        this.pnrLocator = this.page.locator('div.pnr[data-id="pnr"]');
        this.paymentDeclineBannerLocator = this.page.getByText(/payment declined by gateway/i).first();
    }

    private async waitForConfirmationOutcome(timeoutMs = 3000): Promise<"pnr" | "decline" | "timeout"> {
        const pnrPromise = this.pnrLocator.waitFor({ state: "visible", timeout: timeoutMs })
            .then(() => "pnr" as const)
            .catch(() => null);

        const declinePromise = this.paymentDeclineBannerLocator.waitFor({ state: "visible", timeout: timeoutMs })
            .then(() => "decline" as const)
            .catch(() => null);

        const timeoutPromise = new Promise<"timeout">((resolve) => {
            setTimeout(() => resolve("timeout"), timeoutMs);
        });

        return (await Promise.race([pnrPromise, declinePromise, timeoutPromise])) ?? "timeout";
    }

    async assertPaymentDeclineBannerVisible(){
        await expect(this.paymentDeclineBannerLocator).toContainText(/payment declined by gateway/i);
    }

    async checkPnrVisible(){
        await expect(this.pnrLocator).toBeVisible();
    }

    async checkAllSetHeadingVisible(){
        await expect(this.page.getByRole('heading', { name: "You're all set!" })).toBeVisible();
    }

    async checkConfirmationOutcome(){
        const outcome = await this.waitForConfirmationOutcome();

        if (outcome === "decline") {
            await this.assertPaymentDeclineBannerVisible();
            return "decline";
        }

        if (outcome === "pnr") {
            await this.checkPnrVisible();
            await this.checkAllSetHeadingVisible();
            return "pnr";
        }

        throw new Error("Confirmation page did not show either a PNR or a payment decline banner within the expected timeout.");
    }

    async checkConfirmationDetailsVisible(){
        return this.checkConfirmationOutcome();
    }
}
