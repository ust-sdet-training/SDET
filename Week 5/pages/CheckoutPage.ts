import { expect, type Page} from "@playwright/test"


export class CheckoutPage{
    constructor(private readonly page: Page) {}
    private orderBtn = () => this.page.getByRole('button', {name: "Place order"});
    private getneworderid = () => this.page.locator('p').filter({hasText:"confirmed"}).textContent();
    static orderNo : string
    // orderNo = () => this.page.locator('p').filter({hasText:"confirmed"}).textContent();

    async order(){
        const orderRes = this.page.waitForResponse(
            (r) => r.url().includes('/orders') && r.status() === 201
        );
        await this.orderBtn().click();
        await orderRes;
        // CheckoutPage.orderNo = await this.page.locator('p').filter({hasText:"confirmed"}).textContent() as string;
        CheckoutPage.orderNo = await this.page.getByText('ORD-').textContent() as string;

        // orderNo = () => this.page.locator('p').filter({hasText:"confirmed"});

        
    }

    
}