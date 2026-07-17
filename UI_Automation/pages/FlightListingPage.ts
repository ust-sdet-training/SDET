import {Page, Locator} from '@playwright/test';
import { DynamicLocators } from '../locators/DynamicLocators';

export class FlightsPage{

    constructor(private readonly page : Page){}

    flightTab = () : Locator => this.page.getByRole("heading", {level:1});

    countFlights = () : Locator => this.page.locator("#result-live-count");
    flightCards = () : Locator => this.page.locator(".flight-card");

    airlineFilter = (airline: string) : Locator => DynamicLocators.airlineFilter(this.page, airline);
    departureTimeFilter = (slot: string) : Locator => DynamicLocators.departureTimeFilter(this.page,slot);
    sortTypeFilter = (sortBy: string) : Locator => DynamicLocators.sortType(this.page, sortBy); 
    
    priceFilter = () : Locator => this.page.getByRole("slider", {name: 'Maximum price'});

    bookButton = () : Locator => this.page.getByRole("button", {name: "Book"}).first();

    async flightCounter(){
        return  Number(
            await this.countFlights().textContent()
        );
    }

    async flightsVisible(){
        return await this.flightCards().count();
    }

    async chooseAirline(airline: string){
        
    await this.airlineFilter(airline)
        .scrollIntoViewIfNeeded();

        await this.airlineFilter(airline).check();
        await this.page.pause();
    }

    async chooseDepartureTime(timeSlot: string){
        await this.departureTimeFilter(timeSlot).check();
    }

    async chooseSortType(tab: string){
        await this.sortTypeFilter(tab).click();
    }


    async filteringPrice(paise: string){
        await this.priceFilter().fill(paise);
    }

    async clickBooking(){
        await this.bookButton().click();
    }




}
