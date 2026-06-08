import { Page ,Locator } from '@playwright/test';

export class CheckoutPage {

    constructor(private readonly page: Page){}

    checkoutTitleHeading = () : Locator => this.page.getByRole('heading',{level:1});
    deliveryAddressInput = () : Locator => this.page.getByLabel('Delivery address');
    deliverySlotInput = () : Locator => this.page.getByLabel('Delivery slot');
    paymentMethodInput = () : Locator => this.page.getByLabel('Payment method');
    couponCodeInput = () : Locator => this.page.getByLabel('Coupon code');
    placeOrderButton = () : Locator => this.page.getByRole('button',{name:'Place order'});
    confirmationText = () : Locator => this.page.locator('#confirmation-title');
    viewOrdersButton = () : Locator => this.page.getByRole('button',{name:'View orders'});

    async isCheckoutTitleHeadingVisible(): Promise<boolean>{
       return await this.checkoutTitleHeading().isVisible();
    }

    async isConfirmationTextDisplayed(): Promise<boolean>{
       return await this.confirmationText().isVisible();
    }

    async enterDeliveryAddress(address: string){
        await this.deliveryAddressInput().fill(address);
    }

    async selectDeliverySlot(slot: string){
        await this.deliverySlotInput().selectOption(slot);
    }

    async selectPaymentMethod(method: string){
        await this.paymentMethodInput().selectOption(method);
    }

    async enterCouponCode(code: string){
        await this.couponCodeInput().fill(code);
    }

    async clickOnPlaceOrderButton(){
        await this.placeOrderButton().click(); 
    }

    async clickOnViewOrdersutton(){
        await this.viewOrdersButton().click();
    }

}