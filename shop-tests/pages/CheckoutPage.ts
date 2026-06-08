import { Page, expect } from "@playwright/test";
 
export class CheckoutPage {
 
constructor(readonly page: Page){}
 
private email =this.page.getByLabel('Email');
 
private address =this.page.getByLabel('Delivery address');
 
private slot =this.page.getByRole('combobox',{name:'Delivery slot'});
 
private payment =this.page.getByRole('combobox',{name:'Payment method'});
 
private coupon =this.page.getByLabel('Coupon code');
 
private total =this.page.getByTestId('checkout-total');
 
private placeOrderButton =this.page.getByRole('button',{name:/place order/i});
 
private success =this.page.getByRole('heading',{name:/thank you for your order/i});
 
async fillCheckout(){
 
await expect(this.page).toHaveURL(/\/checkout/);
 
await expect(this.email).toHaveValue('customer@example.com');
 
await this.address.fill('UST Campus, Bengaluru');
 
await expect(this.address).toHaveValue('UST Campus, Bengaluru');
 
await this.slot.selectOption('Weekend priority delivery');
 
await this.payment.selectOption('UPI');
 
await this.coupon.fill('UST10');
 
await expect(this.total).toBeVisible();
 
}
 
async placeOrder(){
 
await Promise.all([this.page.waitForResponse(r=>r.url().includes('/orders') && r.status()===201),
 
this.placeOrderButton.click()]);}
 
async verifyOrder(){
 
await expect(this.page).toHaveURL(/\/orders|\/checkout/);
 
await expect(this.success).toBeVisible();
 
}
 
}