import { Page, expect} from "@playwright/test";
import { FlightDetailsPage } from "../pages/FlightDetailsPage";

export class FlightDetailsFlow{
    private readonly flightDetailsPage: FlightDetailsPage;

    private selectedSeat = "";

    constructor(private readonly page: Page){
        this.flightDetailsPage = new FlightDetailsPage(page);
    }

    async bookSeat(): Promise<string>{
        this.selectedSeat = await this.flightDetailsPage.bookFirstAvailableSeat();
        return this.selectedSeat;
    }

    async verifySeatisSelected(){
        await expect(this.flightDetailsPage.selected_Seat_N())
                    .toHaveAttribute("data-seat", this.selectedSeat)
    }

    async verfiySeatIsbooked(){
        await expect(this.flightDetailsPage.viewBookedSeat())
                    .toContainText(this.selectedSeat);
    }

    async continueToPassengerDetails(){
        await this.flightDetailsPage.continueToPassengerDetails();
    }

    async verfiySeatIsBooked(seat: string){
        await expect(this.flightDetailsPage.viewBookedSeat()).toContainText(seat);
    }


}