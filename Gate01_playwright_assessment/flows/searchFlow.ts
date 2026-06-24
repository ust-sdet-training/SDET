import {Page} from '@playwright/test'
import { nykaaLandingPage } from '../pages/nykaaLandingPage'
export class searchFlow
{
    readonly nykaaLanding;
    constructor(private readonly page: Page)
    {
    this.nykaaLanding=new nykaaLandingPage(page);
    }

    async searchFunc(itemName:string): Promise<void>
    {
     await this.nykaaLanding.search_product(itemName);
    }
    async viewProduct():Promise<void>
    {
     await this.nykaaLanding.viewTheProduct();
    }
}