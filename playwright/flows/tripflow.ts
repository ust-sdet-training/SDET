import { Page } from "playwright/test";
import { LoginPage } from "../pages/loginPage";
import { BusListingPage } from "../pages/busListingPage";
import { SearchPage } from "../pages/searchPage";
import { SeatPage } from "../pages/seatPage";
import { PassengerDetailsPage } from "../pages/passengerDetailsPage";
import { PaymentPage } from "../pages/paymentPage";
import { ConfirmationPage } from "../pages/confirmationPage";
export class tripflow{
    private readonly loginpage:LoginPage;
    private readonly searchpage:SearchPage;
    private readonly buslistingpage:BusListingPage;
    private readonly seatpage:SeatPage;
    private readonly passengerdetailspage:PassengerDetailsPage;
    private readonly paymentpage:PaymentPage;
    private readonly confirmationpage:ConfirmationPage;
    constructor(private readonly page:Page){
        this.loginpage = new LoginPage(page);
        this.buslistingpage = new BusListingPage(page);
        this.searchpage = new SearchPage(page);
        this.seatpage = new SeatPage(page);
        this.passengerdetailspage = new PassengerDetailsPage(page);
        this.paymentpage = new PaymentPage(page);
        this.confirmationpage = new ConfirmationPage(page);
    }

    async makeALoginWith(email:string,password:string){
        await this.loginpage.gotoLogin();
        await this.loginpage.enterEmail(email);
        await this.loginpage.enterPassword(password);
        await this.loginpage.signIn();
    }

    async selectTheRoute(from:string,to:string){
        await this.searchpage.selectBuses();
        await this.searchpage.enterTheFromAddress(from);
        await this.searchpage.enterTheToAddress(to);
    }

    async bookFor(days:number){
        await this.searchpage.calender(days);
    }

    async searchBus(){
        await this.searchpage.clickSearch();
    }

    async goToBusListingPageFor(seater:string){
        await this.buslistingpage.goToSeatSection();
        await this.buslistingpage.goToSeatsof(seater);
    }

    async selectAvailableSeat(deck:string){
        await this.seatpage.selectTheDeck(deck);
    }

    async goToPassengerDetails(){
       await this.seatpage.passengerDetails();
    }

    async makeAPaymentFor(deck:string,firstname:string,lastname:string,age:number,gender:string,email:string,phone:string){
        await this.passengerdetailspage.enterTheContactDetails(email,phone);
        await this.passengerdetailspage.enterTheTravellerDetails(deck,firstname,lastname,age,gender);
        await this.passengerdetailspage.proceedToPayment();
    }
    
    async paywith(cardName:string,cardNumber:string,expiry:string,cvv:string){
       await this.paymentpage.enterCardDetails(cardName,cardNumber,expiry,cvv);
       await this.paymentpage.clickPay();
    }

    async validateBookingStatus(){
        const status = await this.confirmationpage.bookingStatus();
        return status;
    }

    async validateThePayement500(){
        
        const errorMessage = await this.paymentpage.error();
         await this.paymentpage.goToMyTrips();
         return errorMessage;
    }


}
