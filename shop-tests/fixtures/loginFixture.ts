import {test as base,expect} from "@playwright/test"
import { Loginflow } from "../flows/LoginFlows"

export const test =  base.extend<{login:Loginflow}>({
    login: async({page},use)=>{
        await use (new Loginflow(page));
    }
})

export {expect} from '@playwright/test';