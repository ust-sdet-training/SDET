import { expect, Page } from "@playwright/test";

export class ConfirmationPage{
    constructor(private page:Page){}
    async verifyBooking(){
        await expect(this.page.getByRole("heading",{name:"You're all set! 🎉"})).toBeVisible();
        await expect(this.page.getByText("Booking reference (PNR)")).toBeVisible();
        await expect(this.page.getByText("Amount paid")).toBeVisible();
    }
}