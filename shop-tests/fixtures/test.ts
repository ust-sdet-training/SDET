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
export const products = {
  footwear: [
    {
      name: "Running Shoes",
      stock: 18
    }
  ],

  bags: [
    {
      name: "Travel Backpack",
      stock: 11
    }
  ],

  electronics: [
    {
      name: "Noise Canceling Headphones",
      stock: 7
    },
    {
      name: "Kids Learning Tablet",
      stock: 5
    }
  ],

  fitness: [
    {
      name: "Insulated Water Bottle",
      stock: 42
    },
    {
      name: "Yoga Mat",
      stock: 23
    },
    {
      name: "Resistance Bands Kit",
      stock: 31
    }
  ],

  apparel: [
    {
      name: "Rain Jacket",
      stock: 9
    }
  ],

  workspace: [
    {
      name: "Smart Desk Lamp",
      stock: 16
    }
  ],

  grocery: [
    {
      name: "Organic Snack Box",
      stock: 35
    }
  ],

  home: [
    {
      name: "Ceramic Dinner Set",
      stock: 6
    }
  ],

  beauty: [
    {
      name: "Skin Care Travel Kit",
      stock: 14
    }
  ]
};
