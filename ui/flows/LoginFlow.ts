import { LoginPage } from '../pages/LoginPage';

export class LoginFlow {

    constructor(private loginPage: LoginPage) {}

    async login() {

        await this.loginPage.loginToApplication();

    }

}