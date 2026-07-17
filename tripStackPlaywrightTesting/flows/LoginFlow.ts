import { LoginPage } from '../pages/LoginPage';

export class LoginFlow {

    constructor(private loginPage: LoginPage) {}

    async login(email: string, password: string): Promise<void> {

        await this.loginPage.navigate();

        await this.loginPage.openLogin();

        await this.loginPage.verifyLoginPageLoaded();

        await this.loginPage.enterEmail(email);

        await this.loginPage.enterPassword(password);

        await this.loginPage.clickSignIn();

    }

}