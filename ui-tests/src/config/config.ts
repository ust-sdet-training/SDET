export const config = {
  baseUrl: process.env.BASE_URL ?? 'https://tripstack.doomple.com',
  email: process.env.TEST_USER_EMAIL ?? 'laura@tripstack.test',
  password: process.env.TEST_USER_PASSWORD ?? 'Password@123'
} as const;
