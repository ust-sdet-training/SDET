import {Page} from "@playwright/test"
import { ConfirmationPageLocators } from "../locators/ConfirmationPageLocators"
export class ConfirmationPage{

    constructor(private readonly page:Page){}

    async bookingStatus(){
        
        return await  ConfirmationPageLocators.bookStatus(this.page).textContent()
    }
   
}