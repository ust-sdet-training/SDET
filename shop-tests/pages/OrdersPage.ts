import { expect, type Page } from "@playwright/test";
import { CheckoutPage } from "./CheckoutPage";

export class OrdersPage {

    private tablerows = ()=> this.page.locator('tbody tr')


constructor(private readonly page: Page) {}

async verifyorders(checkoutpage : CheckoutPage){


    await this.tablerows().first().locator('td').getByRole('button').click()



    

    await checkoutpage.checkorderid(await this.page.getByTestId('selected-order-id').textContent() as string)

}

}
