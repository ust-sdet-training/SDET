import {Page, Locator} from '@playwright/test';

export class FlightDetailsPage{

    constructor(private readonly page : Page){}

    availableSeats = () : Locator => this.page.locator(".seat.available:not(.occupied):not(.is-occupied)");
    
    firstAvailable_Seat = () : Locator => this.availableSeats().first();

    selected_Seat_N = () : Locator => this.page.locator(".seat.is-selected");
    
    continueButton = () : Locator => this.page.getByRole("button", {name: "Continue to passenger details"})
    viewSeat = () : Locator => this.page.locator("#sel-readout");

    async bookFirstAvailableSeat(): Promise<string>{
        const seatNumber = await this.firstAvailable_Seat().getAttribute("data-seat");
        await this.firstAvailable_Seat().click();
        return seatNumber ?? "";
    }

    async continueToPassengerDetails(){
        await this.continueButton().click();
    }

    viewBookedSeat(): Locator{
        return this.viewSeat();
    }
}
