export class Mask {

    static password(value: string): string {

        return "*".repeat(value.length);

    }

}