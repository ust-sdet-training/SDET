import {test as base} from "@playwright/test";
import { SearchFlow } from "../flows/SearchFlow";

export const test = base.extend<{searchFix: SearchFlow}> ({
    searchFix: async ( {page}, use ) => {
        await use(new SearchFlow(page));
    }
})

export { expect } from "@playwright/test";