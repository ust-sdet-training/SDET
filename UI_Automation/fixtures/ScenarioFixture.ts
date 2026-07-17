import { test as base } from "../fixtures/log-and-evidences";
import { HomeFlow } from "../flows/HomeFLow";
import { LoginFlow } from "../flows/LoginFlow";
import { FlightListingFlow } from "../flows/FlightListingFlow";
import { FlightDetailsFlow } from "../flows/FlightDetailsFlow";
import { PassengerDetailFlow } from "../flows/PassengerDetailFlow";
import { PaymentFlow } from "../flows/PaymentFlow";
import { ConfirmationFlow } from "../flows/ConfirmationFlow";
import { MyTripsFlow } from "../flows/MyTripsFlow";

type pageFixture = {
    home : HomeFlow,
    login : LoginFlow
    flights : FlightListingFlow,
    flight: FlightDetailsFlow,
    passenger: PassengerDetailFlow,
    payment: PaymentFlow,
    confirmation: ConfirmationFlow,
    mytrips: MyTripsFlow

};

export const test = base.extend<pageFixture>({
    home : async ({page}, use) => {
        await use(new HomeFlow(page))
    },
    login : async ({page}, use) => {
        await use(new LoginFlow(page))
    },
    flights : async ({page}, use) => {
        await use(new FlightListingFlow(page))
    },
    flight : async ({page}, use) => {
        await use(new FlightDetailsFlow(page))
    },
    passenger : async ({page}, use) => {
        await use(new PassengerDetailFlow(page))
    },
    payment : async ({page}, use) => {
        await use(new PaymentFlow(page))
    },
    confirmation : async ({page}, use) => {
        await use(new ConfirmationFlow(page))
    },
    mytrips : async ({page}, use) => {
        await use(new MyTripsFlow(page))
    }

})
export { expect } from "@playwright/test";