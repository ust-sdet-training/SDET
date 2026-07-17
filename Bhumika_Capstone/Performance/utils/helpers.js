export function sleep(seconds) {
    return new Promise(resolve => setTimeout(resolve, seconds * 1000));
}

export function randomNumber(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}