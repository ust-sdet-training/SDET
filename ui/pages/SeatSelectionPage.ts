import { expect, Page } from '@playwright/test';
import { BasePage } from './BasePage';

export class SeatSelectionPage extends BasePage {

    constructor(page: Page) {
        super(page);
    }


    // Locators

    private cabin = () =>
        this.page.locator('[data-layout="cabin"]');


    private availableSeats = () =>
        this.page.locator('.seat.available');


    private selectedSeats = () =>
        this.page.locator('.seat.selected');


    private continueButton = () =>
        this.page.locator('#continue-btn');



    // Verify Seat Map

    async verifySeatMap() {

        await expect(this.cabin())
            .toBeVisible({
                timeout:15000
            });

    }



    // Select Seat

    async selectRandomSeat() {


        await this.verifySeatMap();


        const seatCount =
            await this.availableSeats().count();


        console.log(
            "Available seats:",
            seatCount
        );


        expect(seatCount)
            .toBeGreaterThan(0);



        const randomIndex =
            Math.floor(
                Math.random() * seatCount
            );


        console.log(
            "Selected seat index:",
            randomIndex
        );



        const seat =
            this.availableSeats()
                .nth(randomIndex);



        await seat.scrollIntoViewIfNeeded();



        await seat.click();



        // Wait for UI state update
        await this.page.waitForTimeout(2000);



        // Verify seat selected
        await expect(
            seat
        ).toHaveClass(
            /selected/,
            {
                timeout:15000
            }
        );



        console.log(
            "Seat selected successfully"
        );


    }



    // Continue Booking

    async continueBooking() {


        const button =
            this.continueButton();



        await expect(button)
            .toBeVisible({
                timeout:15000
            });



        // wait until application enables button
        await expect
        (
            button
        )
        .toBeEnabled({
            timeout:30000
        });



        await button.click();

    }



    // Complete Flow

    async selectSeatAndContinue() {


        await this.selectRandomSeat();


        await this.continueBooking();



        await expect(this.page)
            .toHaveURL(
                /book\/passenger/,
                {
                    timeout:30000
                }
            );

    }

}