
import {Page} from "@playwright/test"
import { SeatPageLocators } from "../locators/SeatPageLocators"
export class SeatPage{

    constructor(private readonly page:Page){}

    async selectTheDeck(deck: string) {
          await SeatPageLocators
        .selectTheAvailableDeck(this.page, deck)
        .click();
       }

    async passengerDetails(){
        await this.page.getByRole("button",{name:"Continue to passenger details"}).click();
        
    }  
}