export const PNR_REGEX = /^TS-\d+-\d{4}$/;

export function pnrRegexForEmp(empId: string): RegExp {
    return new RegExp(`^TS-${empId}-\\d{4}$`);
}

export function generateRandomPhone(): string {
    const digits = Math.floor(1000000000 + Math.random() * 8999999999);
    return digits.toString();
}