import { Environment } from "../config/environment";

export class Secrets {

    static username() {

        return Environment.username;

    }

    static password() {

        return Environment.password;

    }

}