import { expect, test } from "@playwright/test";
import { logger } from "../utils/logger";

test.describe("Float Math Example", () => {

    test("Before Fix - Float calculation causes paise mismatch", () => {

        
        const unitPrice = 333.00;
        const tax = 16.65;

        const total = unitPrice + tax;

        logger.info(`Total: ${total}`);

        const totalFor3 = total * 3;

        logger.info(`Total for 3: ${totalFor3}`);

        //not - its not equal to 1048.95 - because of floating point precision issues
        expect(totalFor3).not.toBe(1048.95);
    });

    test("After Fix - Integer paise calculation is exact", () => {

        // interger paise solves float math issues
        const unitPaise = 33300;
        const taxPaise = 1665;

        const totalPaise = unitPaise + taxPaise;

        logger.info(`Total paise: ${totalPaise}`);
        expect(totalPaise).toBe(34965);

        const totalFor3 = totalPaise * 3;

        logger.info(`Total for 3: ${totalFor3}`);
        expect(totalFor3).toBe(104895);
    });

});
