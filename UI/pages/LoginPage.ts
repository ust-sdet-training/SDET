import {Page} from "@playwright/test"

export class LoginPage{

    page : Page;

    constructor(page: Page){
        this.page = page
    }

    async goto(){
        await this.page.goto('/login')
    }

    async login(email: string, password: string){
        await this.page.getByRole('link', { name: /Log in/i }).click();
        await this.page.getByRole('textbox', { name: /Email/i }).click();
        await this.page.getByRole('textbox', { name: /Email/i }).fill(email);
        await this.page.getByRole('textbox', { name: /Password/i }).click();
        await this.page.getByRole('textbox', { name: /Password/i }).fill(password);
        await this.page.getByRole('button', { name: /Sign in/i }).click();

    }

}``