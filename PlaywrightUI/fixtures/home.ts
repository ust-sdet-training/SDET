import {test as base,expect} from '@playwright/test';
import { HomeFlow } from '../flows/HomeFlow';;

export const test = base.extend<{home:HomeFlow}>({
    home:async({page},use)=>{
        await use(new HomeFlow(page));
    }
})

export {expect} from '@playwright/test';