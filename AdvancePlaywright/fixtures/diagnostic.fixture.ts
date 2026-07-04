import { test as base, expect } from "@playwright/test";
import { logger } from "../src/logger";
import crypto from "node:crypto";

type DiagnosticFixture = {
    correlationId: string;
    log: typeof logger;
};

export const test = base.extend<DiagnosticFixture>({

    correlationId: async ({}, use) => {

        const id = crypto.randomUUID();

        await use(id);

    },

    log: async ({ correlationId }, use) => {

        logger.info(`Test Started - ${correlationId}`);

        await use(logger);

        logger.info(`Test Finished - ${correlationId}`);
    }

});

export { expect };