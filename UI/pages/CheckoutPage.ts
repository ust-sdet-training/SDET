import {Page} from "@playwright/test"

export class CheckoutPage{

    page : Page;

    constructor(page: Page){
        this.page = page
    }


    async enterFirstName(firstname : string){
            await this.page.getByRole('textbox', { name: /First name/i }).click();
            await this.page.getByRole('textbox', { name: /First name/i }).fill(firstname);
    }

    async enterLastName(lastname : string){
        await this.page.getByRole('textbox', { name: /Last name/i }).click();
        await this.page.getByRole('textbox', { name: /Last name/i }).fill(lastname);
    }

    async enterEmail(email : string){
        await this.page.getByRole('textbox', { name: /Email/i }).click();
        await this.page.getByRole('textbox', { name: /Email/i }).fill(email);
    }

    async enterAge(age : number){
        await this.page.getByRole('spinbutton', { name: /Age/i }).click();
        await this.page.getByRole('spinbutton', { name: /Age/i }).fill(age.toString());
    }



    async enterGender(gender : string){
        await this.page.getByRole('textbox', { name: /Gender/i }).click();
        await this.page.getByRole('textbox', { name: /Gender/i }).fill(gender);
    }

    async enterPhNo(phno : number){
        await this.page.getByRole('textbox', { name: /Phone number/i }).click();
        await this.page.getByRole('textbox', { name: /Phone number/i }).fill(phno.toString());
    }

    async continue(){
        await this.page.getByRole('button', { name: /Continue/i }).click();
    }
}