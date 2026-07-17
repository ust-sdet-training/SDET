import {Page} from "@playwright/test"
import { PassengerDetailsPageLocators } from "../locators/PassengerDetailsPageLocators";
export class PassengerDetailsPage{

    constructor(private readonly page:Page){}

    async enterTheContactDetails(email:string,phone:string){
        await this.page.getByLabel("email").isVisible();
        await this.page.getByLabel("email").fill(email);

         await this.page.getByLabel("phone").isVisible();
        await this.page.getByLabel("phone").fill(String(phone));
    }

    async enterTheTravellerDetails(deck:string,firstname:string,lastname:string,age:number,gender:string){
       await this.page.getByLabel(`First name (seat ${deck})`).fill(firstname);
        await this.page.getByLabel(`Last name (seat ${deck})`).fill(lastname);
        await this.page.getByLabel(`Age (seat ${deck})`).fill(String(age));
        await PassengerDetailsPageLocators.selectGender(this.page,deck,gender);
    }

    async proceedToPayment(){
        await this.page.getByRole("button",{name:"Continue to payment"}).click();
    }
}