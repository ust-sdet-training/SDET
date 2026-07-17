import { Page } from "@playwright/test";
import { Logger } from "../src/logger/logger";

export class SeatSelectionPage {

    constructor(private page: Page) {}

    async chooseSeat(seatNumber: string) {

        Logger.info(`Selecting Seat ${seatNumber}`);
        await this.page.getByText(seatNumber, { exact: true }).click();

    }

    async continue() {

        await this.page
            .getByRole("button", {
                name: /continue/i
            })
            .click();

    }

}