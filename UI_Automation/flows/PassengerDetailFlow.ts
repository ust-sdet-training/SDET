import { Page, expect} from "@playwright/test";
import { PassengerDetailsPage } from "../pages/PassengerDetailsPage";

export class PassengerDetailFlow{
    private readonly passengerPage: PassengerDetailsPage;

    constructor(private readonly page: Page){
        this.passengerPage = new PassengerDetailsPage(page);
    }

    async fillDetails(seat:string, fname: string, lname: string, age: string, gender: string
    ){
        await this.passengerPage.fillFirstName(seat, fname);
        await this.passengerPage.fillLastName(seat, lname);
        await this.passengerPage.fillAge(seat, age);
        await this.passengerPage.fillGender(seat, gender);
    }

    async fillContactDetails(email: string, phone: string){
        await this.passengerPage.enterEmail(email);
        await this.passengerPage.enterPhoneNumber(phone)
    }

    async goToPayment(){
        await this.passengerPage.continueToPayment();
    }

}