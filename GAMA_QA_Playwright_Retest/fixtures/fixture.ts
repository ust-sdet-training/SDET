import {expect,test as base} from "@playwright/test"
import {HomeFlow} from "../flows/HomeFlow.ts"
export const test=base.extend<{
    home:HomeFlow;
    }>
({
    home:async ({page},use)=>{
        await use (new HomeFlow(page))
    }
})
export{expect} from "@playwright/test"