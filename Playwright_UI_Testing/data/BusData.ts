export class BusData {

    static readonly from = "Lucknow (LKO) — CCS";

    static readonly to = "Bengaluru (BLR) — Kempegowda Intl";

    static readonly seatNumber = "L5";

    static readonly firstName = "Saiteja";

    static readonly lastName = "Kodi";

    static readonly age = "24";

    static readonly gender = "male";

    static readonly date = (() => {
        const date = new Date();
        date.setDate(date.getDate() + 27);
        return date.toISOString().split("T")[0];
    })();
}