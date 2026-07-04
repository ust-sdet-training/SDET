const tsConfig = {
  compilerOptions: {
    target: "ES2022",
    module: "NodeNext",
    moduleResolution: "NodeNext",
    types: ["node", "@playwright/test"],
    strict: true,
    esModuleInterop: true,
    skipLibCheck: true
  },
  include: ["**/*.ts"]
} as const;

export default tsConfig;