import { Page, Locator } from "@playwright/test";

export class DynamicLocators{
    static airlineFilter( page: Page, airline: string)
     : Locator {
        return page.getByRole("checkbox", {name: airline});
    }

    static departureTimeFilter(page: Page, slot: string)
     : Locator {
        return page.getByRole("checkbox", {name: slot});
    }

    static sortType(page: Page, sortBy: string)
     : Locator {
        return page.getByRole("button", {
            name: sortBy
        })
    }

    static seat(
        page: Page,
        seatNumber: string
    ): Locator {
        return page.locator(
            `[data-seat="${seatNumber}"]`
        );
    }

    static passengerFirstName(
        page: Page,
        seat: string
    ): Locator {
        return page.getByRole("textbox", {
            name: `First name (seat ${seat})`
        });
    }

    static passengerLastName(
        page: Page,
        seat: string
    ): Locator {
        return page.getByRole("textbox", {
            name: `Last name (seat ${seat})`
        });
    }

    static passengerAge(
        page: Page,
        seat: string
    ): Locator {
        return page.getByRole("spinbutton", {
            name: `Age (seat ${seat})`
        });
    }

    static passengerGender(
        page: Page,
        seat: string
    ): Locator {
        return page.getByRole("combobox", {
            name: `Gender (seat ${seat})`
        });
    }


}