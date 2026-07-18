import { expect,Page } from "@playwright/test";
import{LoginPage} from "../pages/LoginPage"
import {BusSearchPage} from "../pages/BusSearchPage"
export class TripStackFlow  {

    private readonly loginPage: LoginPage;
    private readonly busSearchPage :BusSearchPage;
  
    constructor(private readonly page: Page) 
    {

        this.loginPage = new LoginPage(page);
        this.busSearchPage= new BusSearchPage(page);

        
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
    

}
