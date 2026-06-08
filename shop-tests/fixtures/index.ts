import {test as base,expect} from "@playwright/test"
import { Loginflow } from "../flows/LoginFlows"
import { shopFlow } from "../flows/shopFlow";

export const test =  base.extend<{login:Loginflow,shop:shopFlow}>({
    login: async({page},use)=>{
        await use (new Loginflow(page));
    },
    shop: async({page},use)=>{
            await use (new shopFlow(page));
        }
})

export {expect} from '@playwright/test';