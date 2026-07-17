import {test as base} from '../fixtures/artifact-fixture'
import { tripflow } from '../flows/tripflow';

type PageFixtures = {
  trip:tripflow;
};

export const test = base.extend<PageFixtures>({

    trip:async({page},use)=>{
        await use(new tripflow(page));
    }

})

export {expect} from "@playwright/test"

