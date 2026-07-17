import { HomePage } from "../pages/HomePage";
import { SearchResultPage } from "../pages/SearchResultPage";
import { PlanePage } from "../pages/PlanePage";
import { SearchData } from "../test-data/SearchData";
import { TestInfo } from "@playwright/test";
import { AppLogger } from "../utils/logger";
import { LoginPage } from "../pages/LoginPage";
import { PaymentPage } from "../pages/PaymentPage";
import { config } from "../utils/env";
import { UserData } from "../test-data/UserData"
import { expect } from "@playwright/test";
import { MyTripsPage} from "../pages/MyTripsPage";
import dotenv from "dotenv";

export class BookingFlow {

    constructor(private login: LoginPage, private home: HomePage,private result: SearchResultPage,private plane: PlanePage, private payment: PaymentPage, private trips: MyTripsPage, private log: AppLogger)
     {}
    
    async bookFlight(testInfo: TestInfo) {
        
        await this.login.open();
        await expect(this.login.header()).toBeVisible();

        this.log.info("ENTERING EMAIL AND PASSWORD")
        await this.login.login(config.email,config.password);
        this.log.info("SUCCESSFULLY LOGGED IN")

        this.log.info("ENTERING FROM AND TO CITY")
        await this.home.selectFromDestination(
            SearchData.FROM_CITY
        );

        this.log.info("Test log");
        await this.home.selectToDestination(
            SearchData.TO_CITY
        );

        this.log.info("SELECTING DATE")
        await this.home.selectDate(
            SearchData.DATE
        );

        await testInfo.attach("Home Page", {
        body: await this.home.page.screenshot(),
        contentType: "image/png",
        });

        this.log.info("SEARCHING")
        await this.home.search();
        await expect(this.result.searchResults()).toBeVisible();

        await testInfo.attach("Results", {
        body: await this.result.page.screenshot(),
        contentType: "image/png",
        });

        await expect(this.result.firstFlight()).toBeVisible();
        this.log.info("FLIGHTS ARE AVAILABLE")
        await this.result.openFirstResult();

        await this.plane.selectSeat();

        await expect(this.plane.header()).toBeVisible();

        this.log.info("ENTERING PASSENGER DETAILS")
        await this.plane.enterDetails(UserData.FIRST_NAME,UserData.LAST_NAME,UserData.AGE,config.email,UserData.PHONE);

        this.log.info("ENTERING PAYMENT DETAILS")
        const pnr = await this.payment.fillDetails(config.cardName, config.cardNo, config.expiry,config.cvv);
        await testInfo.attach("Successful Booking", {
        body: await this.payment.page.screenshot(),
        contentType: "image/png",
        });

        await this.trips.open();
        await this.trips.verifyBooking(pnr);
        await testInfo.attach("Previous Bookings", {
            body: await this.trips.page.screenshot(),
            contentType: "image/png",
        });



        this.log.info("REPEATING FOR ROUND TRIP")
        //RETURN TRIP
        await this.home.open();
        await this.home.selectFromDestination(
            SearchData.TO_CITY
        );
        this.log.info("Test log");
        await this.home.selectToDestination(
            SearchData.FROM_CITY
        );

        await this.home.selectDate(
            SearchData.RETURN_DATE
        );

        await this.home.search();
        await expect(this.result.searchResults()).toBeVisible();

        await expect(this.result.firstFlight()).toBeVisible()
        await this.result.openFirstResult();

        await this.plane.selectSeat();

        await expect(this.plane.header()).toBeVisible();
        await this.plane.enterDetails(UserData.FIRST_NAME,UserData.LAST_NAME,UserData.AGE,config.email,UserData.PHONE);
        await this.payment.fillDetails(config.cardName, config.cardNo, config.expiry,config.cvv);

        

    }

}