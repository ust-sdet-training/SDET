import {Page } from "@playwright/test";
import { LoginPage } from "../pages/LoginPage";

export class LoginFlow {

    loginPage : LoginPage;

    constructor(private page: Page) { 

        this.loginPage = new LoginPage(page);

    }

    async loginUser(username : string,password : string){

        this.loginPage.login(username,password)

    }


}