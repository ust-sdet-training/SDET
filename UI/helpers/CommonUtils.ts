export class CommonUtils {

    static randomEmail() {

        return `user${Date.now()}@mail.com`;

    }

    static randomNumber() {

        return Math.floor(Math.random() * 100000);

    }

}