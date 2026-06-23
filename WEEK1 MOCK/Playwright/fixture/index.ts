import {expect,test as base} from "@playwright/test"
import {ShopFlow} from "../flow/ShopFlow.ts"
export const test=base.extend<{
    shop:ShopFlow;
    }>
    
({
    shop:async ({page},use)=>{
        await use (new ShopFlow(page))
    }
})
export{expect} from "@playwright/test"