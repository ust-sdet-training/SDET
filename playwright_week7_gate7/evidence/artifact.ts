import { expect, test as base } from '../evidence/diagnose';

type Evidence = Record<string, unknown>;

export const test = base.extend<{ evidence: Evidence }>({
    evidence: async ({}, use, testInfo) => {

        const evidence: Evidence = {};


        await use(evidence); 


        for (const [key, value] of Object.entries(evidence)) {
            try {
                // Determine format based on the data type
                const isString = typeof value === 'string';
                const fileName = isString ? `${key}.txt` : `${key}.json`;
                const contentType = isString ? 'text/plain' : 'application/json';
                const body = isString ? value : JSON.stringify(value, null, 2);

                await testInfo.attach(fileName, {
                    body,
                    contentType
                });
            } catch (err) {
                console.error(`Failed to attach evidence for "${key}":`, err);
            }
        }
    }
});

export { expect };