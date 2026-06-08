import {expect, type Page} from "@playwright/test";
import { SearchPage } from "./SearchPage";

export class CheckoutPage{
    constructor(private readonly page:Page){}
    async checkoutToOrders(){
    await this.page.goto('/checkout');
    await this.page.getByRole("button",{name:"Place order"}).click();
    await expect(this.page.getByRole("heading",{name:"Thank you for your order"}));
    await this.page.getByRole("button",{name:"View orders"}).click();
    await this.page.goto("/orders");
    await expect(this.page.getByRole("heading",{name:"Orders"})).toBeVisible();
    }

}