import { FlightHomePage } from "../pages/FlightHomePage";
import { FlightListPage } from "../pages/FlightListPage";
import { CheckoutPage } from "../pages/CheckoutPage";
import { CabinSelectPage } from "../pages/CabinSelectPage";
import { PaymentPage } from "../pages/PaymentPage";

import usercheckoutdata from '../test-data/UserCheckoutinfo.json'

import cartdetails from '../test-data/CardDetails.json'

import {Page} from "@playwright/test"

export class TicketBookFlow {

    flighthome : FlightHomePage;
    private log: any;
    private evidence: any
    private test: any
    private expect: any
    private tripId: string

    constructor(private page: Page,log: any, evidence: any, test :any, expect : any) { 

        this.flighthome = new FlightHomePage(page);
        this.log = log
        this.evidence = evidence
        this.test = test
        this.expect = expect
        this.tripId = ""

    }

    async bookTicket (user:keyof typeof usercheckoutdata,departure : string, destination : string):Promise<string>{
        
        await this.flighthome.setDepatureLocation(departure)
        await this.flighthome.setDestinationLocation(destination)

        var plus14Days = await this.flighthome.setDate(14)

        this.evidence.date = plus14Days;


        this.log.info("Searching for flights",{ 
        departure: departure, 
        destination: destination, 
        date: plus14Days 
    })

    

    await this.flighthome.search()

    var flightlist = new FlightListPage(this.page);

    flightlist.bookFirstFlight()

    var cabinselect = new CabinSelectPage(this.page);

    cabinselect.getFirstAvailableSeat()


    cabinselect.continue()

    
    this.log.info("Inputting Passenger details")

    var checkoutPage = new CheckoutPage(this.page);

    await checkoutPage.enterFirstName(usercheckoutdata[user].firstname);

    await checkoutPage.enterLastName(usercheckoutdata[user].lastname);

    await checkoutPage.enterAge(usercheckoutdata[user].age);

    await checkoutPage.enterEmail(usercheckoutdata[user].email);

    await checkoutPage.enterPhNo(usercheckoutdata[user].phoneno);


    await this.page.screenshot({ 
            path: 'screenshots/checkoutdetails.png'
        });

    
        await this.test.info().attach("Checkout Sample Details", {
            path: "screenshots/checkoutdetails.png",
            contentType: "image/png"
        });

    

    await checkoutPage.continue()

    this.log.info("Entering the card details")

    var paymentPage = new PaymentPage(this.page);

    await paymentPage.setCardDetails(cartdetails[user])

  
    try {
        await paymentPage.payPrice();
        this.tripId = await paymentPage.getTripId();
    } catch (error) {
       
            this.log.error("Payment failed")
            return ""
        
    }


    this.tripId = await paymentPage.getTripId();

    this.evidence.tripId = this.tripId;


    await this.page.goto('/my-trips', {waitUntil: "domcontentloaded"});

    await this.page.screenshot({ 
            path: 'screenshots/flight-booked.png'
        });

    
        await this.test.info().attach("Flight Booked Proof", {
            path: "screenshots/flight-booked.png",
            contentType: "image/png"
        });

        this.log.info("Flight Booked Successfully")


        return this.tripId
    }



    async cancelTrip(tripId:string){

    this.log.info("Flight Cancellation Process Initiated")

    const tripCard = await this.page.locator(`[data-id="trip-${tripId}"]`);

    await this.expect(tripCard.locator('[data-id="state"]')).toHaveText(/Confirmed/i);

    await tripCard.getByRole('button',{name:/Cancel/i}).click()

    this.log.info("Flight Cancelled Successfully")

    await this.page.reload;

    await this.page.screenshot({ 
            path: 'screenshots/flight-cancelled.png'
        });

    
        await this.test.info().attach("Flight Cancelled Proof", {
            path: "screenshots/flight-cancelled.png",
            contentType: "image/png"
        });

        }
}