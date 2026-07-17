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




}