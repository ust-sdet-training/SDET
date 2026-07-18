const today = new Date();

const travelDate = today.toISOString().split('T')[0];

export const bookingData = {
    from: 'BOM',
    to: 'GOI',
    tripType: 'Round Trip',
    travelDate,
    returnAfterDays: 8
};

export const employee = {
    employeeId: process.env.EMPLOYEE_ID!,
    email: process.env.EMAIL!,
    password: process.env.PASSWORD!
};

export const loginData = {
    email: process.env.EMAIL!,
    password: process.env.PASSWORD!
};

export const passengerData = {
    firstName: 'Lahari',
    lastName: 'Gandla',
    age: '23',
    email: 'lahari@gmail.com',
    phone: '6785432123'
};

export const paymentData = {
    cardHolder: 'Lahari',
    cardNumber: '7898789865437867',
    expiry: '30/30',
    cvv: '5678'
};