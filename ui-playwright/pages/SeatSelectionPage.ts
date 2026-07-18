import { expect, Page } from "@playwright/test";

export class SeatSelectionPage{
    constructor(private page:Page){}
    async chooseSeat(seat:string){
        await expect(
            this.page.getByRole("heading",{
                name:"Choose your seats"
            })
        ).toBeVisible();
        await this.page.getByLabel(seat).click();
        await this.page
            .getByRole("button",{
                name:"Continue to passenger details"
            })
            .click();
    }
}