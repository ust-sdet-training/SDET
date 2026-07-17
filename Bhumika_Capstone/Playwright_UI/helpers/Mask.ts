export class Mask {

    static secret(value: string): string {

        return "*".repeat(value.length);

    }

}