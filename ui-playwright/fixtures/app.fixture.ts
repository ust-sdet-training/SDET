import { test as diagnosticTest, expect } from "./dignostic.fixture";
import { BookingFlow } from "../flows/BookingFlow";
import { BookingData } from "./data.fixture";

type Evidence = Record<string, unknown>;

export const test = diagnosticTest.extend<{
    booking: BookingFlow;
    bookingData: BookingData;
    evidence: Evidence;
}>({

    bookingData: async ({}, use) => {

        await use({

            login: {
                email: "karl@tripstack.test",
                password: "Password@123"
            },

            passenger: {
                firstName: "Hemanth",
                lastName: "Reddy",
                age: "25",
                gender: "male",
                phone: "7301123456"
            },

            payment: {
                cardHolder: "karl",
                cardNumber: "1234567812345678",
                expiry: "0130",
                cvv: "897"
            },

            oneWay: {
                from: "jai",
                fromAirport: "Jaipur JAI",
                to: "bom",
                toAirport: "Mumbai BOM",
                date: "2026-07-17",
                route: "JAI → BOM",
                flight: "IndiGo 6E-483"
            },

            returnTrip: {
                from: "bom",
                fromAirport: "Mumbai BOM",
                to: "jai",
                toAirport: "Jaipur JAI",
                date: "2026-08-15",
                route: "BOM → JAI",
                flight: "IndiGo 6E-483-R"
            }

        });

    },

    booking: async ({ page }, use) => {

        await use(new BookingFlow(page));

    },

    evidence: async ({}, use, testInfo) => {

        const evidence: Evidence = {};

        await use(evidence);

        for (const [name, value] of Object.entries(evidence)) {

            if (value === undefined) continue;

            const isText = typeof value === "string";

            await testInfo.attach(`${name}.${isText ? "txt" : "json"}`, {
                body: isText
                    ? value
                    : JSON.stringify(value, null, 2),
                contentType: isText
                    ? "text/plain"
                    : "application/json",
            });

        }

    },

});

export { expect };