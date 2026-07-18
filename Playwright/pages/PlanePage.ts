import { expect, Page } from "@playwright/test";
import { BasePage } from "./BasePage";

export class PlanePage extends BasePage {

    constructor(page: Page) {
        super(page);
    }

    private firstAvailableSeat = this.page.locator(".seat.available").first();
    private continueBtn = this.page.getByRole("button",{name:"Continue to passenger details"});
    private firstName = this.page.getByRole('textbox', { name: /First name/ });
    private lastName = this.page.getByRole('textbox', { name: /Last name/ });
    private age = this.page.getByRole('spinbutton', { name: /Age/ });
    private email = this.page.getByRole('textbox', { name: 'Email' });
    private phNo = this.page.getByRole('textbox', { name: 'Phone number' })
    private continueToPaymentBtn = this.page.getByRole("button", {name: "Continue to payment"});
    header = () => this.page.getByRole("heading", {name:"Who's travelling?"});

    async selectSeat(){
        await expect(this.firstAvailableSeat).toBeVisible();
        await this.firstAvailableSeat.click();
        
        // const seatNumber = "15C";
        // await this.page
        //     .locator(`[data-seat="${seatNumber}"]`)
        //     .click();
        await expect(this.continueBtn).toBeEnabled();
        await this.continueBtn.click();
    }

    async enterDetails(passengerFirstName: string, passengerLastName: string, passengerAge: string, passengerEmail: string, phone: string){
        await this.firstName.fill(passengerFirstName);
        await this.lastName.fill(passengerLastName);
        await this.age.fill(passengerAge);
        await this.email.fill(passengerEmail);
        await this.phNo.fill(phone);
        await this.continueToPaymentBtn.click();
    }



}
