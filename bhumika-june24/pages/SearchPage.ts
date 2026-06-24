import { Page } from "@playwright/test";
export class SearchPage {

    constructor(private page: Page) {}

    async goto() {
     await this.page.goto('https://www.nykaa.com/', { waitUntil: 'domcontentloaded' });
    }
    logo(){
        return this.page.getByRole("link", { name: "Nykaa Logo" }).click();
    }
    searchBox() {
        return this.page.getByRole("textbox", {name: "Search on Nykaa"  }).click();
    }

    category() {
       return this.page.getByRole('textbox', { name: 'Search on Nykaa' }).fill('watch');
    }

    watchesCategory() {
        return this.page.getByText('watches', { exact: true }).click();
    }

   
invalidSearch() {
        return this.page.getByRole('textbox', { name: 'Search on Nykaa' }).fill('xya4567980');
    }
    clickSearch() {
        return this.page.getByRole('textbox', { name: 'Search on Nykaa' }).press('Enter');
    }
    text() {
        return this.page.getByText('Thanks for visiting our');
    }
    unfortunatelyText() {
        return this.page.getByText('Unfortunately, we couldn’t');
    }
    backToHome() {
        return this.page.getByRole('button', { name: 'Back to Home' }).click();
    }
    getBeautyBonanza() {
        return this.page.getByRole('link', { name: 'BEAUTY BONANZA Get Your' }).click();
    }
  
}