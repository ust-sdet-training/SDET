import {test as artifactTest,expect} from "./loggerFixture"
 
type Artifacts = Record<string, unknown>;
 
export const test = artifactTest.extend<{
  artifacts: Artifacts;
}>({
  artifacts: async ({}, use, testInfo) => {
    const artifacts: Artifacts = {};
 
    await use(artifacts);
 
    for (const [name, value] of Object.entries(artifacts)) {
      if (value === undefined) {
        continue;
      }
 
      const isText = typeof value === "string";
 
      await testInfo.attach(
        `${name}.${isText ? "txt" : "json"}`,
        {
          body: isText
            ? value
            : JSON.stringify(value, null, 2),
          contentType: isText
            ? "text/plain"
            : "application/json",
        }
      );
    }
  },
});
 
export { expect };