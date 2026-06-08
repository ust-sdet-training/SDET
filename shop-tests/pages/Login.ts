import {Page} from '@playwright/test';

export class Login {

    constructor(private page: Page){}

    async login(email:string,password:string){
        await this.page.goto('/login');
        await this.page.getByLabel('Email').fill(email);
        await this.page.getByLabel('Password').fill(password);
        await this.page.getByRole('button',{name:'Sign in'}).click();
    }
}