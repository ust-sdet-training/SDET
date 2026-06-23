import {test as base,expect} from "@playwright/test"
import { HomeFlow } from "../flow/HomeFlow";

type Testflow = {
    home:HomeFlow;
};

export const test = base.extend<Testflow> ({
    home: async({page},use)=>{
        await use (new HomeFlow(page));
    },
})

export {expect};