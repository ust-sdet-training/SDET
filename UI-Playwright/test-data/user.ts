export const User = {
    from: 'Bengaluru',
    fromOption: 'Bengaluru BLR',
    to: 'Chennai',
    toOption: 'Chennai MAA',
    date: (() => {
        const date = new Date();
        date.setDate(date.getDate() + 9);
        return date.toISOString().split('T')[0]; // YYYY-MM-DD
    })(),
    sortBy: 'Price',
    flightName: 'IndiGo 6E-494',
    seat: '7D',
    firstName: 'Ivan',
    lastName: 'Iyengar',
    gender: 'Male',
    age: '23',
    bookingStatus: 'CONFIRMED',
    cancelBookingStatus: 'REFUNDED'
};
