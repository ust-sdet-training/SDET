import { Secrets } from "../config/Secrets";

export class Users {

    static readonly username = Secrets.get("TRIPSTACK_USERNAME");

    static readonly password = Secrets.get("TRIPSTACK_PASSWORD");

    static readonly email = Secrets.get("TRIPSTACK_USERNAME");

    static readonly phone = "9876543210";
}