import {test as base,expect} from "@playwright/test"
import { shopFlow } from "../flows/shopFlow";

export const test =  base.extend<{shop:shopFlow}>({
    shop: async({page},use)=>{
        await use (new shopFlow(page));
    }
})

export {expect} from '@playwright/test';