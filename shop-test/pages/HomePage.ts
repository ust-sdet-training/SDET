import { expect, type Page } from "@playwright/test";

export class HomePage{
    constructor(private readonly page: Page){}

    async open(){
        await this.page.goto('/home');
    }

    async previewProduct(){
        // var lognPage= await this.page.goto('/login');

        const previewApi= this.page.waitForResponse(
            r=> r.url().includes('/api/products') && r.status()===200
        )
        await this.page.getByRole("link", {name: "Preview products"}).click();
        await previewApi;
    }

    // async expectError(message:string){
    //     await expect(this.page.getByRole("alert")).toContainText(message);
    // }

}
