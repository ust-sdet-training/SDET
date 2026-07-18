export const config = {
    baseUrl: __ENV.BASE_URL || "https://tripstack.doomple.com",
    users: Number(__ENV.USERS || 10),
    duration: __ENV.DURATION || "30s"
};