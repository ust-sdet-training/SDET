import {expect,test as base} from "../evidence/artifact"
import {TripStackFlow} from "../flow/TripStackFlow"
export const test=base.extend<{
    book:TripStackFlow;
    }>
    
({
    book:async ({page},use)=>{
        await use (new TripStackFlow(page))
    }
})
export{expect} from "@playwright/test"