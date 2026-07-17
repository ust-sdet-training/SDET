import {Page, Locator} from '@playwright/test';

export class PassengerDetailsPage{

    constructor(private readonly page : Page){}

    firstName = (seat: string) : Locator => this.page.getByRole('textbox', { name: `First name (seat ${seat})` });
    lastName = (seat: string) : Locator => this.page.getByRole('textbox', { name: `Last name (seat ${seat})` });
    age = (seat: string) : Locator => this.page.getByRole('spinbutton', { name: `Age (seat ${seat})` });
    gender = (seat: string) : Locator => this.page.getByRole("combobox", {name: `Gender (seat ${seat}d)`});
    
    inputEmail = () : Locator => this.page.getByRole("textbox", {name: "Email"});
    inputPhoneNum = () : Locator => this.page.getByRole("textbox", {name: "Phone number"});

    continueToPaymentButton = () : Locator => this.page.getByRole("button", {name: "Continue to payment"});

    async fillFirstName(seat: string, fname: string){
        await this.firstName(seat).fill(fname);
    }

    async fillLastName(seat: string, lastname: string){
        await this.lastName(seat).fill(lastname);
    }

    async fillAge(seat: string,age: string){
        await this.age(seat).fill(age);
    }

    async fillGender(seat: string, gender: string){
        await this.gender(seat).selectOption(gender.toLowerCase());
    }

    async enterEmail(email: string){
        await this.inputEmail().fill(email);
    }

    async enterPassword(phoneNum: string){
        await this.inputPhoneNum().fill(phoneNum);
    }

    async continueToPayment(){
        await this.continueToPaymentButton().click();
    }

}
 