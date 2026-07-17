import {Page, Locator} from '@playwright/test';

export class FlightDetailsPage{

    constructor(private readonly page : Page){}

    seat_1B = () : Locator => this.page.locator("[data-seat='1B']");
    continueButton = () : Locator => this.page.getByRole("button", {name: "Continue to passenger details"})
    viewSeat = () : Locator => this.page.locator("#sel-readout");

    async bookSeat1B(){
        await this.seat_1B().click();
        // await this.page.pause()
    }

    async checkPassengerDetails(){
        await this.continueButton().click();
    }

    viewBookedSeat(): Locator{
        return this.viewSeat();
    }

}
