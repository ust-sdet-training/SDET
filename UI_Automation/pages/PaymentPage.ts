import {Page, Locator} from '@playwright/test';

export class PaymentPage{

    constructor(private readonly page : Page){}

    inputNameOnCard = () : Locator => this.page.getByRole("textbox", {name: "Name on card"});
    inputCardNumber = () : Locator => this.page.getByRole("textbox", {name: "Card number"});
    inputExpiry = () : Locator => this.page.getByRole("textbox", {name: "Expiry"});
    inputCVV = () : Locator => this.page.getByRole("textbox", {name: "CVV"});

    paymentButton = () : Locator => this.page.locator('button[type="submit"]');


    async enterNameOnCard(name: string){
        await this.inputNameOnCard().fill(name);
    }

    async enterCardNumber(number: string){
        await this.inputCardNumber().fill(number);
    }

    async enterExpiry(expiry: string){
        await this.inputExpiry().fill(expiry);
    }

    async enterCVV(cvv: string){
        await this.inputCVV().fill(cvv);
    }

    async clickPayment(){
        await this.paymentButton().click();
    }
}