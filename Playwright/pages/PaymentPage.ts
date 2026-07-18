import {Page} from "@playwright/test"
import { BasePage } from "./BasePage"
import { SearchData } from "../test-data/SearchData";
import { config } from "../utils/env";
import { expect } from "@playwright/test"

export class PaymentPage extends BasePage{
    constructor(page: Page)
    {super(page)}

    private cardName = this.page.getByLabel("Name on card");
    private cardNo = this.page.getByLabel("Card number");
    private expiry = this.page.getByLabel("Expiry");
    private cvv = this.page.getByLabel("CVV");
    private pnr = this.page.locator("[data-id='pnr']");
    private payBtn = this.page.getByRole("button", {name:/Pay/});
    private myTrips = this.page.getByRole("link", {name:"My Trips"});
    private paymentError = this.page.getByRole("alert")
    
    async getPnr(): Promise<string> {
        return (await this.pnr.innerText()).trim();
    }

    async fillDetails(name: string, number: string, expiry: string, cvv: string): Promise<string>{
    await this.cardName.fill(name);
    await this.cardNo.fill(number);
    await this.expiry.fill(expiry);
    await this.cvv.fill(cvv);
    await this.payBtn.click();

    const confirmed = this.page.getByText("CONFIRMED", { exact: true });
    const errorAlert = this.paymentError;

    // Wait for whichever outcome actually happens, instead of a blind isVisible() check
    const result = await Promise.race([
        confirmed.waitFor({ state: "visible", timeout: 30000 }).then(() => "confirmed"),
        errorAlert.waitFor({ state: "visible", timeout: 30000 }).then(() => "error"),
    ]).catch(() => "timeout");

    if (result === "error") {
        const errorText = await errorAlert.innerText().catch(() => "");
        console.log(`Payment error: ${errorText}`);
        return "";
    }

    if (result === "timeout") {
        console.log("Payment result not determined within timeout — neither confirmation nor error appeared");
        return "";
    }

    await expect(this.page.getByText(/TS/)).toBeVisible();
    await expect(this.page.locator("[data-id='pnr']")).toContainText("TS-1023-");

    const pnr = (await this.pnr.innerText()).trim();
    return pnr;
}

    
}