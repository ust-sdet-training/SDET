export const testUsers = {
  customer: {
    email: process.env.TEST_EMAIL || "customer@example.com",
    password: process.env.TEST_PASSWORD || "Password@123",
    displayName: "Customer User"
  },
  deckDemo: {
    email: "user@test.com",
    password: "Secret123",
    displayName: "Demo User"
  },
  locked: {
    email: "locked@example.com",
    password: "Password@123"
  },
  invalid: {
    email: "invalid@example.com",
    password: "WrongPassword123"
  }
};
