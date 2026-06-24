import {expect,test as base} from "@playwright/test"
import {TestFlow} from "../flows/TestFlow.ts"
export const test=base.extend<{
    stest:TestFlow;
    }>
    
({
    stest:async ({page},use)=>{
        await use (new TestFlow(page))
    }
})
export{expect} from "@playwright/test"