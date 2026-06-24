import {Page,Locator} from '@playwright/test';

export class AuthPage {

    constructor(readonly page:Page){}

    continueAsGuestButton = () :Locator => this.page.getByRole('button',{name:'Continue as guest'});

    
    
    async clickContinueAsGuestButton(){
        await this.continueAsGuestButton().click();
    }

}