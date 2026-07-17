import { Page, expect} from "@playwright/test";
import { FlightsPage } from "../pages/FlightListingPage";

export class FlightListingFlow{
    private readonly flightsPage: FlightsPage;

    constructor(private readonly page: Page){
        this.flightsPage = new FlightsPage(page);
    }

    async verifyflightCount(count: number){
        const actualCount = await this.flightsPage.flightCounter()
        expect(actualCount).toBe(count);
    }

    async verifyflightCountAndCardCountMatch(){
        const count = await this.flightsPage.flightCounter();

        // await expect.poll(() =>this.flightsPage.flightsVisible())
        //         .toBe(1)

        expect(await this.flightsPage.flightsVisible()).toBe(Number(count));
    }

    async filterAirline(airline:string){

        await this.flightsPage.chooseAirline(airline);
    }

    async filterTimeSlot(slot:string){
        await this.flightsPage.chooseDepartureTime(slot);
    }
    
    async filterSortTabs(tab: string){
        await this.flightsPage.chooseSortType(tab);
    }

    async filterPrice(price: number){
        const paiseString = String(price*100);
        await this.flightsPage.filteringPrice(paiseString);
    }

    async goToFlightDetailsPage(){
        await this.flightsPage.clickBooking();
    }
    
}