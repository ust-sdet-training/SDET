import {test as base, expect} from "@playwright/test"
import { ShopFlow } from "../flows/ShopFlow";


export const test = base.extend({ shop: async ({page}, use) =>{
    await use(new ShopFlow(page));
}
})

export {expect};