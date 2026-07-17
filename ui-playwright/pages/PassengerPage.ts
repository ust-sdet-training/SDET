import { expect, Page } from "@playwright/test";

export class PassengerPage{

    constructor(private page:Page){}

    async enterPassengerDetails(
        first:string,
        last:string,
        age:string,
        gender:string,
        email:string,
        phone:string
    ){

        await expect(
            this.page.getByRole("heading",{
                name:"Who's travelling?"
            })
        ).toBeVisible();

        await this.page.locator('[id^="name-"]').fill(first);
        await this.page.locator('[id^="lastname-"]').fill(last);
        await this.page.locator('[id^="age-"]').fill(age);
        await this.page.locator('[id^="gender-"]').selectOption(gender);

        await this.page.getByRole("textbox",{name:"Email"}).fill(email);
        await this.page.getByRole("textbox",{name:"Phone number"}).fill(phone);

        await this.page
            .getByRole("button",{name:"Continue to payment"})
            .click();
    }
}