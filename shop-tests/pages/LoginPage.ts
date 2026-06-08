import { Locator ,Page } from "@playwright/test";

export class LoginPage {

  constructor(private readonly page: Page) {}

  emailInput = () : Locator => this.page.getByLabel('Email');
  passwordInput = () : Locator => this.page.getByLabel('Password');
  countryInput = () : Locator => this.page.getByLabel('Country');
  rememberMeInput = () : Locator => this.page.getByLabel('Remember me');
  signInButton = (): Locator => this.page.getByRole('button',{name:'Sign in'});
  loginError = () : Locator => this.page.getByTestId('login-error');
  
  async login(email: string, password: string, country: string) {
    await this.emailInput().fill(email);
    await this.passwordInput().fill(password);
    await this.countryInput().selectOption(country);
    await this.rememberMeInput().check();
    await this.signInButton().click();
  }

  async getErrorMessage():Promise<string>{
    return await this.loginError().textContent() || '';
  }

  async clickOnSignInButton(){
    await this.signInButton().click();
  }


}
