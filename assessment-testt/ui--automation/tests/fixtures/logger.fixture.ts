import { test as base } from "@playwright/test";

export type TestFixtures = {
  logger: Logger;
};

export class Logger {
  private rows: Array<{ step: string; result: string; details?: string }> = [];

  info(step: string, result: string, details?: string) {
    this.rows.push({ step, result, details });
  }

  getTable(): string {
    const header = ["Step", "Result", "Details"];
    const rows = this.rows.map((row) =>
      [row.step, row.result, row.details ?? ""].join(" | "),
    );
    return [header.join(" | "), "--- | --- | ---", ...rows].join("\n");
  }
}

export const test = base.extend<TestFixtures>({
  logger: async ({}, use) => {
    const logger = new Logger();
    await use(logger);
  },
});

export { expect } from "@playwright/test";
