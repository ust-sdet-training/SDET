import { Page,Locator } from '@playwright/test';

export class HomePage{

    constructor(private readonly page:Page){}

    titleHeading = (): Locator => this.page.locator('#page-title');

    signInButton = (): Locator => this.page.getByRole('link',{name:'Sign in'});
    previewProductsButton = (): Locator => this.page.getByRole('link',{name:'Preview products'});

    async goToHomePage(){
        await this.page.goto('/');
    }

    async clickOnSignInButton() {
        await this.signInButton().click();
    }

    async getTitleHeading():Promise<string>{
        return await this.titleHeading().textContent() + '';
    }

    async clickOnPreviewProducts(){
        await this.previewProductsButton().click();
    }

}