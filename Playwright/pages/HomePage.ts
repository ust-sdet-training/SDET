
import {Page} from "@playwright/test"
import { BasePage } from "./BasePage"
import { SearchData } from "../test-data/SearchData";
import { config } from "../utils/env";

export class HomePage extends BasePage{
    constructor(page: Page)
    {super(page)}

    private fromCity = this.page.getByRole("combobox",{name:"From"} );
    private toCity = this.page.getByRole("combobox",{name:"To"});
    private datePicker = this.page.getByRole("textbox", {name:"Date"});
    private searchBtn = this.page.getByRole("button", {name:"Search"});
    header = () => this.page.getByRole("heading",{name: "Book flights & buses across India"});

    async open(){
        await this.page.goto(config.baseUrl);
    }

    async selectFromDestination(from : string){
        await this.fromCity.fill(from);
        await this.page.getByRole("option", {
            name: new RegExp(from, "i")
        }).click();
    }

    async selectToDestination(to : string){
        await this.toCity.fill(to);
        await this.page.getByRole("option", {
            name: new RegExp(to, "i")
        }).click();
    }

    async selectDate(date : string){
        console.log(date);
        await this.datePicker.fill(date);
    }

    async search(){
        await this.searchBtn.click();
    }
}