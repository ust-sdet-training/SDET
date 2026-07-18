import { Page ,expect} from "@playwright/test";

export class PaymentPage{
    constructor(private page:Page){}
    async pay(
        holder:string,
        card:string,
        expiry:string,
        cvv:string
    ){
        await this.page.getByRole("textbox",{name:"Name on card"}).fill(holder);
        await this.page.getByRole("textbox",{name:"Card number"}).fill(card);
        await this.page.getByRole("textbox",{name:"Expiry"}).fill(expiry);
        await this.page.getByRole("textbox",{name:"CVV"}).fill(cvv);
        await this.page.getByRole("button",{name:/Pay/}).click();
    }
    async verifyConnectionResetError() {
 
        await expect(this.page.getByText("payment gateway connection reset")).toBeVisible();
 
        await expect(this.page.getByRole("heading", {name:"Secure checkout"})).toBeVisible();
 
        await expect(this.page).toHaveURL(/payment/);
    }
}