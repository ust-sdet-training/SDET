import { expect, type Page} from "@playwright/test"


export class OrderPage{
    constructor(private readonly page: Page) {}
    
    async checkForOrder(orderNo : string){
        await expect(this.page.getByRole("table", {name: "Recent retail orders"})).toContainText(orderNo);
    }

}