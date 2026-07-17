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
import dotenv from "dotenv";

export class InvalidSearchFlow {

    constructor(private login: LoginPage, private home: HomePage,private result: SearchResultPage, private log: AppLogger)
     {}
    
    async bookFlight(testInfo: TestInfo) {
        
        await this.login.open();
        await expect(this.login.header()).toBeVisible();

        this.log.info("ENTERING EMAIL AND PASSWORD")
        await this.login.login(config.email,config.password);
        this.log.info("SUCCESSFULLY LOGGED IN")

       

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
        await expect(this.result.page.getByRole("heading",{name:"No flights found"})).toBeVisible();

        await testInfo.attach("Results", {
        body: await this.result.page.screenshot(),
        contentType: "image/png",
        });

        

    }

}