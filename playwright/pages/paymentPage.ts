import {expect, Page} from "@playwright/test"
export class PaymentPage{

    constructor(private readonly page:Page){}


    async enterCardDetails(cardName:string,cardNumber:string,expiry:string,cvv:string){
        await this.page.getByLabel("Name on card").fill(cardName);
         await this.page.getByLabel("Card number").fill(cardNumber);
        await this.page.getByLabel("Expiry").fill(expiry);
        await this.page.getByLabel("CVV").fill(cvv);
    }

    async clickPay(){
        await this.page.getByRole("button",{name:/Pay/i}).click();
    }

    async error(){
        const errorText = await this.page.getByRole("alert").textContent();    
        return errorText;
}
    async goToMyTrips(){
        await this.page.getByRole('link',{name:"My Trips"}).click();
    }

    
}