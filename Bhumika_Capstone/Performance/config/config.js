export const config = {
    baseUrl: __ENV.BASE_URL || "http://localhost:8080",
    users: Number(__ENV.USERS || 10),
    duration: __ENV.DURATION || "30s"
};