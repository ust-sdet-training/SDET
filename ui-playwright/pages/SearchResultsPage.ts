import { expect, Page } from "@playwright/test";

export class SearchResultsPage{
    constructor(private page:Page){}
    async bookFlight(route:string,flight:string){
        await expect(
            this.page.getByRole("heading",{
                name:`Flights: ${route}`
            })
        ).toBeVisible();
        await expect(
            this.page.getByText(flight)
        ).toBeVisible();
        await this.page
            .getByLabel(flight)
            .getByRole("button",{name:"Book"})
            .click();
    }
}