import { Page, Locator ,expect} from '@playwright/test';

export class PaymentPage {

    constructor(readonly page: Page) { }


    paymentPageHeading = (): Locator => this.page.getByRole('heading', { name: 'Choose payment method' });

    async isPaymentPageHeadingVisible() {
      await  expect(this.paymentPageHeading()).toBeVisible();
    }



}