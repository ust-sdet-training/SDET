import { Secrets } from "../config/Secrets";

export const Users = {
    employee: {
        email: Secrets.get("USERNAME"),
        password: Secrets.get("PASSWORD")
    }
};