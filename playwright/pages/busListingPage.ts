import {Page} from "@playwright/test"
import { BusListingPageLocators } from "../locators/BusListingPageLocators";
export class BusListingPage{

    constructor(private readonly page:Page){}


    async goToSeatSection(){
        await BusListingPageLocators.selectSeatSection(this.page).click();
    }
   
    async goToSeatsof(seater:string){
       await BusListingPageLocators.getTheSeaterBus(this.page).getByRole("button",{name:"Select Seats"}).click();
    }  
}