import { Page, expect} from "@playwright/test";
import { FlightDetailsPage } from "../pages/FlightDetailsPage";

export class FlightDetailsFlow{
    private readonly flightDetailsPage: FlightDetailsPage;

    constructor(private readonly page: Page){
        this.flightDetailsPage = new FlightDetailsPage(page);
    }

    async bookSeat(){
        await this.flightDetailsPage.bookSeat1B();
    }

    async continueToPassengerDetails(){
        await this.flightDetailsPage.checkPassengerDetails();
    }

    async verfiySeatIsBooked(){
        await expect(this.flightDetailsPage.viewBookedSeat()).toContainText("1B");
    }


}