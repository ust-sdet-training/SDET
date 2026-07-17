import { Secrets } from "../config/Secrets";

export class Users {

    static readonly username = Secrets.get("TEST_USERNAME");

    static readonly password = Secrets.get("PASSWORD");

    static readonly email = Secrets.get("TEST_USERNAME");

    static readonly phone = "9876543210";
}