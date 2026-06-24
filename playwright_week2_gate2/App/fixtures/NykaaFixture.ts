import{test as base} from "@playwright/test"
import { NykaaFlow} from "../flows/NykaaFlow"

export const test =
base.extend
<{
    home : NykaaFlow
}>
({
    home : async ({page}, use)=>
    {
        await use(new NykaaFlow(page))
    }
})