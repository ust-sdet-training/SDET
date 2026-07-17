import { Page, expect} from "@playwright/test";
import { MyTripsPage } from "../pages/MyTripsPage";
import { EnvCheck } from "../support/EnvCheck";

export class MyTripsFlow{
    private readonly mytripsPage: MyTripsPage;


    constructor(private readonly page: Page){
        this.mytripsPage = new MyTripsPage(page);

    }
    async verifyTicketisBooked(){
        const bookingCount =await (await this.mytripsPage.getBookingCard()).count();
        expect(bookingCount).toBeGreaterThan(0);
    }

    async verifyBookingTitle(pnr: string){
        await expect(await this.mytripsPage.getBookingTitle()).toBeVisible();
        await expect(await this.mytripsPage.getBookingTitle()).toContainText(pnr);
    }

    async verifyBookingStatus(status: string){
        await expect(await this.mytripsPage.getyBookingStatus()).toBeVisible();
        await expect(await this.mytripsPage.getyBookingStatus()).toContainText(status);
    }

    async verifyBookingSeat(seat: string){
        await expect(await this.mytripsPage.getBookingSeat()).toBeVisible();
        await expect(await this.mytripsPage.getBookingSeat()).toContainText(seat);
    }
}