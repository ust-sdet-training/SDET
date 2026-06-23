import { Page, Locator ,expect} from '@playwright/test';

export class ProductPage {

    constructor(private readonly page: Page) {

    }

    checkPincodeInput = (): Locator => this.page.getByRole('textbox', { name: 'Enter your PIN code' });
    checkButton = (): Locator => this.page.getByText('Check');
    checkGreen = (): Locator => this.page.locator('.text-green-600');
    addToBagButton = (): Locator => this.page.getByRole('button', { name: 'Add to bag' });
    gotToBagButton = (): Locator => this.page.getByRole('button', { name: 'Go To Bag' });


    async checkPincode(pincode: string) {
        await this.checkPincodeInput().click();
        await this.checkPincodeInput().fill(pincode);
        await this.checkButton().click();
    }

    async isPincodeGreen(){
        await expect(this.checkGreen()).toBeVisible();
    }

    async clickAddToBag(){
        await this.addToBagButton().click();
    }

    async clickGoToBag(){
        await this.gotToBagButton().click();
    }
}