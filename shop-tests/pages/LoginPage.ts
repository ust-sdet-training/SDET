import {Page,expect} from "@playwright/test";

export class LoginPage {

    constructor(readonly page: Page){}

    private email =this.page.getByLabel('Email');

    private password =this.page.getByLabel('Password');

    private country =this.page.getByRole('combobox',{name:'Country'});

    private rememberMe =this.page.getByLabel('Remember me');

    private loginButton =this.page.getByRole('button',{name:'Sign in'});

    private userChip =this.page.getByLabel('Signed in user');

    private alert =this.page.getByTestId('login-error');

    async open(){
        await this.page.goto('/login');
        await expect(this.page).toHaveURL('/login');
    }

    async login(email:string,password:string,country='India',remember=false){
        await this.open();

        await this.email.fill(email);
        await expect(this.email).toHaveValue(email);

        await this.password.fill(password);
        await expect(this.password).toHaveValue(password);

        await this.country.selectOption(country);
        await expect(this.country).toHaveValue(country);

        if(remember){
            await this.rememberMe.check();
            await expect(this.rememberMe).toBeChecked();
        }

        await this.loginButton.click();

    }

    async verifySuccess(){
        await expect(this.userChip).toBeVisible();
    }

    async verifyFailure(){
        await expect(this.alert).toBeVisible();
    }

    }