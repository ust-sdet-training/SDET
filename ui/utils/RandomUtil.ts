import { Locator } from '@playwright/test';

export class RandomUtil {

    static async clickRandom(locator: Locator): Promise<void> {

        const count = await locator.count();

        if (count === 0) {
            throw new Error('No elements found.');
        }

        const random = Math.floor(Math.random() * count);

        console.log(`Available : ${count}`);
        console.log(`Selected Index : ${random}`);

        await locator.nth(random).click();
    }

}