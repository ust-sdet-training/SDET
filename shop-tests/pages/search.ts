import type { Page }  from "@playwright/test";
export class searchPage{
    constructor(private readonly page: Page){}

    private box = () => this.page.getByRole("searchbox");
    results = () => this.page.getByTestId("catalog-result-count");
    async search(q: string){
        const r = this.page.waitForResponse(
            (r) => r.url().includes('/api/products') && r.status() === 200);
        await this.box().fill(q);
        await this.box().press("Enter");
        await r;
        // await this.page.getByRole("link", {name: "View ${q}"}).first().click();
    }

}