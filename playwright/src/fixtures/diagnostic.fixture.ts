import { test as base, expect } from "@playwright/test";
import crypto from "node:crypto";
import { BookingFlow } from "../flows/BookingFlow";
import { logger, type AppLogger, redactForLog } from "../utils/logger";
import { getMaskedLocators } from "../utils/mask";
import "../config/constants"

type DiagnosticFixtures = {
    correlationId: string;
    log: AppLogger;
    booking: BookingFlow;
};

export const test = base.extend<DiagnosticFixtures>({

    correlationId: async ({}, use) => {
        await use(crypto.randomUUID());
    },

    log: async ({ page, correlationId }, use, testInfo) => {

        await page.setExtraHTTPHeaders({
            "x-correlation-id": correlationId
        });

        const baseMeta = {
            correlationId,
            project: testInfo.project.name,
            service: "tripstack-playwright",
            specFile: testInfo.file,
            testName: testInfo.title,
            workerIndex: testInfo.workerIndex
        };

        const childLogger = logger.child(baseMeta);

        const lines: string[] = [];

        const diagnosticLogger =
            childLogger as AppLogger & Record<string, unknown>;

        for (const level of ["error", "warn", "info", "debug"] as const) {

            const original = childLogger[level].bind(childLogger);

            diagnosticLogger[level] = (
                message: string,
                meta: Record<string, unknown> = {}
            ) => {

                if (childLogger.isLevelEnabled(level)) {

                    lines.push(
                        JSON.stringify(
                            redactForLog({
                                ...baseMeta,
                                ...meta,
                                level,
                                message,
                                timestamp: new Date().toISOString()
                            })
                        )
                    );

                }

                original(message, meta);

                return diagnosticLogger;

            };

        }

        diagnosticLogger.info("Test Started");

        await use(diagnosticLogger as AppLogger);

        diagnosticLogger.info("Test Finished", {
            status: testInfo.status
        });

        await testInfo.attach("Execution Logs", {
            body: lines.join("\n"),
            contentType: "application/x-ndjson"
        });

        if (testInfo.status !== testInfo.expectedStatus) {

            const screenshot = await page.screenshot({
                mask: await getMaskedLocators(page)
            });

            await testInfo.attach("Failure Screenshot", {
                body: screenshot,
                contentType: "image/png"
            });

        }

    },

    booking: async ({ page, log }, use) => {

        const bookingFlow = new BookingFlow(page, log);

        await use(bookingFlow);

    }

});

export { expect };