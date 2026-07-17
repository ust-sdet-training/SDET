import {Page, Locator} from '@playwright/test';

export class MyTripsPage{

    constructor(private readonly page : Page){}

    bookingCard = () : Locator => this.page.locator(".result");

    latestBooking = () : Locator => this.bookingCard().first();

    bookingNumber = () : Locator => this.page.locator(".title").first(); 
    bookingStatus = () : Locator => this.page.locator(".badge.badge-ok").first();
    bookingSeat = () : Locator => this.page.locator(".meta").first();

    logInButton = () : Locator => this.page.getByRole("button", {name: "Sign in"});


    async getBookingCard(){
        return this.bookingCard();
    }

    async getBookingTitle(){
        return await this.bookingNumber();
    }

    async getyBookingStatus(){
         return await this.bookingStatus();
    }

    async getBookingSeat(){
        return await this.bookingSeat();
    }
}