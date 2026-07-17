export const users = {

    validUser: {
        email: process.env.EMAIL!,
        password: process.env.PASSWORD!

    },

    invalidUser: {
        email: "invalid@test.com",
        password: "Invalid@123"
    }

};