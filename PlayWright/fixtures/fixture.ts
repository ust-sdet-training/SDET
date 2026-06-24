import {test as base, expect} from "@playwright/test"
import { TestFlow } from "../flows/TestFlow"
 
 
export const testing = base.extend({ shop: async ({page}, use) =>{
    await use(new TestFlow(page));
}
})
 
export {expect};