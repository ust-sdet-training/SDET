import { expect,Page } from "@playwright/test";
import{LoginPage} from "../pages/LoginPage"
import {BusSearchPage} from "../pages/BusSearchPage"
import {SeatTypePage} from "../pages/SeatTypePage"
import {PassengerDetailsPage}from  "../pages/PassengerDetailsPage"
import {PaymentPage} from "../pages/PaymentPage"
export class TripStackFlow  {

    private readonly loginPage: LoginPage;
    private readonly busSearchPage :BusSearchPage;
    private readonly seatselectPage:SeatTypePage;
    private readonly enterdetailsPage:PassengerDetailsPage;
    private readonly paymentPage:PaymentPage;

    constructor(private readonly page: Page) 
    {

        this.loginPage = new LoginPage(page);
        this.busSearchPage= new BusSearchPage(page);
        this.seatselectPage =new SeatTypePage(page);
        this.enterdetailsPage=new PassengerDetailsPage(page);
        this.paymentPage=new PaymentPage(page);

        
        
    }

    async login(username:string,password:string) {

        await this.loginPage.openLoginPage();
        await expect(this.page).toHaveURL("/login")
        await this.loginPage.login(username,password);

    }

    async search(from:string,to:string,date:string)
    {
        await this.busSearchPage.openBusPage();
        await this.busSearchPage.fillTripDetails(from,to,date)
    }
    async selectseat()
    {
        await this.seatselectPage.busType();
        await this.seatselectPage.selectSeat();
        // await expect(this.seatselectpage.seatavailablecheck).
        await this.seatselectPage.selectAvailableSeat();
        await this.seatselectPage.proceedToPassengerDeatils();
    }

    async enterdetails(firstname:string,lastname:string,age:string,email:string,ph_no:string)
    {
        await this.enterdetailsPage.enterPassengerDetails(firstname,lastname,age,email,ph_no)
    }
    async payment(firstname:string,cardnumber:string,exp:string,cv:string)
    {

        
        await this.paymentPage.enterPaymentDetails(firstname,cardnumber,exp,cv)
        const paymentError = this.page.getByText('payment gateway connection');
        if (await paymentError.isVisible({ timeout: 3000 }).catch(() => false)) 
        {
        await expect(paymentError).toBeVisible();
        }
        else
        {
        await expect(this.page.getByText('Booking reference (PNR)')).toBeVisible();
        }
        

    }

    async paymentExpectFailure(firstname: string,cardnumber: string,exp: string,cvv: string) 
    {
    await this.paymentPage.enterPaymentDetails(firstname,cardnumber,exp,cvv
    );
}
}
