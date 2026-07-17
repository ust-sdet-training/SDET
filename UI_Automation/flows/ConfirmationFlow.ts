import { Page, expect} from "@playwright/test";
import { ConfirmationPage } from "../pages/ConfirmationPage";

export class ConfirmationFlow{
    private readonly confirmationPage: ConfirmationPage;

    constructor(private readonly page: Page){
        this.confirmationPage = new ConfirmationPage(page);
    }

    async verifyBadgeShowsConfirmed(){
        this.confirmationPage.verifyBadge();
    }

    async checkPNR_Number(): Promise<string>{
        return await this.confirmationPage.getPnrNumber();
    }

    async viewMyTrips(){
        await this.confirmationPage.viewHistoryTrips();
    }
}