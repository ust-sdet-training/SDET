import { Page, expect} from "@playwright/test";
import { LoginPage } from "../pages/LoginPage";
import { EnvCheck } from "../support/EnvCheck";

export class LoginFlow{
    private readonly loginPage: LoginPage;


    constructor(private readonly page: Page){
        this.loginPage = new LoginPage(page);

    }
    async userLogin(){
        await this.loginPage.enterEmail(EnvCheck.TESTUSER_EMAIL);
        await this.loginPage.enterPassword(EnvCheck.TESTUSER_PASSWORD);

        await this.loginPage.clickLogIn();
    }
}