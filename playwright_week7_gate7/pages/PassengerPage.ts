import {Page} from '@playwright/test'

export class Passengerpage
{
    constructor(public readonly page:Page){}


    async passenger(first:string,last:string,age:string,gender:string,email:string,phone:string)
    {
        await this.page.getByRole('textbox',{name:/First/}).fill(first)
        await this.page.getByRole('textbox',{name:/Last/}).fill(last)
        await this.page.getByRole('spinbutton',{name:/Age/}).fill(age)
        await this.page.getByRole('combobox').selectOption(gender);

        await this.page.getByRole('textbox',{name:/Email/}).fill(email)
        await this.page.getByRole('textbox',{name:/Phone/}).fill(phone)

        await this.page.getByRole('button',{name:/Continue/}).click()


    }   
}
