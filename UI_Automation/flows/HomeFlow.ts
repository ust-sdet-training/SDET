import { Page, expect} from "@playwright/test";
import { HomePage } from "../pages/HomePage";

export class HomeFlow{
    private readonly homePage: HomePage;

    constructor(private readonly page: Page){
        this.homePage = new HomePage(page);
    }

    async goToHomePage(){
        await this.homePage.openHomePage();
        await this.page.waitForLoadState("domcontentloaded");
        await expect(this.page).toHaveURL("/");
    }
    async verifyUserOnFlightsTab(){
        const flightTab = this.homePage.flightTab();

        await expect(flightTab).toBeVisible();
        await expect(flightTab).toHaveText("Flights");
        await expect(flightTab).toHaveClass(/active/);
    }

    async fillingTravelDetails(from: string, to: string, days: number){
        await this.homePage.enterFromCity(from);

        await expect(this.homePage.selectedFromOption()).toContainText("LKO");
        
        await this.homePage.enterToCity(to);
        await expect(this.homePage.selectedToOption()).toContainText("DEL")
        
        const departDate = await this.homePage.selectDate(days);
        expect(this.homePage.datePicker()).toHaveValue(departDate);
        await this.homePage.clickSearchBtn();
    }
}