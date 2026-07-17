import { test as base } from "@playwright/test";

export type BookingData = {
    login: {
        email: string;
        password: string;
    };

    passenger: {
        firstName: string;
        lastName: string;
        age: string;
        gender: string;
        phone: string;
    };

    payment: {
        cardHolder: string;
        cardNumber: string;
        expiry: string;
        cvv: string;
    };

    oneWay: {
        from: string;
        fromAirport: string;
        to: string;
        toAirport: string;
        date: string;
        route: string;
        flight: string;
    };

    returnTrip: {
        from: string;
        fromAirport: string;
        to: string;
        toAirport: string;
        date: string;
        route: string;
        flight: string;
    };
};

export const test = base.extend<{
    bookingData: BookingData;
}>({
    bookingData: async ({}, use) => {
        await use({
            login: {
                email: "karl@tripstack.test",
                password: "Password@123",
            },

            passenger: {
                firstName: "Hemanth",
                lastName: "Reddy",
                age: "25",
                gender: "male",
                phone: "7301123456",
            },

            payment: {
                cardHolder: "karl",
                cardNumber: "1234567812345678",
                expiry: "0130",
                cvv: "897",
            },

            oneWay: {
                from: "jai",
                fromAirport: "Jaipur JAI",
                to: "bom",
                toAirport: "Mumbai BOM",
                date: "2026-07-17",
                route: "JAI → BOM",
                flight: "IndiGo 6E-483",
            },

            returnTrip: {
                from: "bom",
                fromAirport: "Mumbai BOM",
                to: "jai",
                toAirport: "Jaipur JAI",
                date: "2026-08-15",
                route: "BOM → JAI",
                flight: "IndiGo 6E-483-R",
            },
        });
    },
});