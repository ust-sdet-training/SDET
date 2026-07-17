import { HomePage } from '../pages/HomePage';
import { PassengerPage } from '../pages/PassengerPage';
import { PaymentPage } from '../pages/PaymentPage';
import { ConfirmationPage } from '../pages/ConfirmationPage';
import { SearchResultsPage } from '../pages/SearchResultsPage';
import { SeatSelectionPage } from '../pages/SeatSelectionPage';

import { routeData } from '../data/route';

export class FlightBookingFlow {

    constructor(

        private homePage: HomePage,
        private searchResultsPage: SearchResultsPage,
        private seatSelectionPage: SeatSelectionPage,
        private passengerPage: PassengerPage,
        private paymentPage: PaymentPage,
        private confirmationPage: ConfirmationPage

    ) {}

    async bookFlight(): Promise<string> {

        await this.homePage.searchFlight(
            routeData.from,
            routeData.to,
            routeData.daysFromToday
        );

        await this.searchResultsPage.selectFirstFlight();

        await this.seatSelectionPage.selectSeatAndContinue();

        await this.passengerPage.fillPassengerDetails();

        await this.paymentPage.makePayment();

        return await this.confirmationPage.verifyBookingConfirmed();

    }

}