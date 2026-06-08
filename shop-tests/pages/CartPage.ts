import { Page, expect } from "@playwright/test";

export class CartPage {

constructor(readonly page: Page){}

private cartCount =this.page.getByTestId('cart-count');

private checkoutButton =this.page.getByRole('button',{name:'Proceed to checkout'});

private total =this.page.getByTestId('order-total');


async verify(){
    // await Promise.all([this.page.waitForResponse(r=>r.url().includes('/api/cart')&&r.request().method()==='GET'&&r.status()===200)]);
    await expect(this.page).toHaveURL(/\/cart/);
    await expect(this.cartCount).toHaveText('1');
    await expect(this.total).toBeVisible();
}

async checkout(){
    
    await this.checkoutButton.click();
    await expect(this.page).toHaveURL(/\/checkout/);
}

}