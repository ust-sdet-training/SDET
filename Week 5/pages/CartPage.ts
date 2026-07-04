import { expect, type Page} from "@playwright/test"

export class CartPage{
    constructor(private readonly page: Page) {}
    cartCount = () => this.page.getByTestId('cart-count');
    private checkoutBtn = () => this.page.getByRole('button', {name: "Proceed to checkout"});
    

    async checkout(){
        const cartRes = this.page.waitForResponse(
            (r) => r.url().includes('/cart') && r.status() === 200
        );

        await this.checkoutBtn().click();
        await cartRes;
    }

}