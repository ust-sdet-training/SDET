import { MyTripsPage } from '../pages/MyTripsPage';

export class MyTripsFlow {

    constructor(private myTripsPage: MyTripsPage) {}

    async verifyBooking(pnr: string) {

        await this.myTripsPage.openMyTrips();

        await this.myTripsPage.verifyBooking(pnr);

    }

    async cancelBooking(pnr: string) {

        await this.myTripsPage.openMyTrips();

        await this.myTripsPage.verifyBooking(pnr);

        await this.myTripsPage.cancelBooking(pnr);

    }

}