import {Page, Locator} from '@playwright/test';

export class HomePage{

    constructor(private readonly page : Page){}

    flightTab = () : Locator => this.page.getByRole("tab", {name: "Flights"});

    fromCity = () : Locator => this.page.getByRole("combobox", {name: "From"});
    fromCity_FirstSuggestion = () : Locator => this.page.getByRole('option').first();
    selectedFromOption = () : Locator => this.page.locator("#home-from-listbox li[aria-selected='true']")
    
    toCity = () : Locator => this.page.getByRole("combobox", {name: "To"});
    toCity_FirstSuggestion = () : Locator => this.page.getByRole('option').first();
    selectedToOption = () : Locator => this.page.locator("#home-to-listbox li[aria-selected='true']")
    
    datePicker = () : Locator => this.page.getByRole("textbox", {name: 'Date'});
    searchButton = () : Locator => this.page.getByRole("button", {name: 'Search'});

    async openHomePage(){
        await this.page.goto("/");
    }

    async isFlightTabActive(){
        return this.flightTab();
    }
    
    async enterFromCity(from: string){
        await this.fromCity().fill(from);
        await this.fromCity_FirstSuggestion().click();
        // return this.fromCity();
    }

    async returnFromCitySuggestion(from: string){
        await this.fromCity().fill(from);
        return this.fromCity_FirstSuggestion.toString();
    }

    async enterToCity(to: string){
        await this.toCity().fill(to);
        await this.toCity_FirstSuggestion().click();
        // return this.toCity();
    }

    async returnToCitySuggestion(to: string){
        await this.fromCity().fill(to);
        return this.toCity_FirstSuggestion.toString();
    }

    async selectDate(daysToAdd: number){
        const curdate = new Date();
        curdate.setDate(curdate.getDate()+daysToAdd);
        const travelDate = curdate.toISOString().split("T")[0];
        await this.datePicker().fill(travelDate);

        return travelDate;
    }

    async clickSearchBtn(){
        await this.searchButton().click();
    }




}


