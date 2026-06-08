import { expect, type Page} from "@playwright/test"


export class ProductPage{
    constructor(private readonly page: Page) {}
    private addBtn = () => this.page.getByRole('button', {name: 'Add to cart'});


    async add_cart(){
        const cartRes = this.page.waitForResponse(
            (r) => r.url().includes('/cart') && r.status() === 200
        );

        await this.addBtn().click();
        await cartRes;

    }

}