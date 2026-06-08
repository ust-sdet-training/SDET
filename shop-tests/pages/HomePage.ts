import { expect, type Page } from "@playwright/test";

export class HomePage {
constructor(private readonly page: Page) {}

    async gotoProducts(){
        this.page.getByRole("link",{name:/products/}).click()
    }
}
  