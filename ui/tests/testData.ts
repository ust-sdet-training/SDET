import 'dotenv/config';

function getJourneyDate(daysAhead = 17) {
  const journeyDate = new Date();
  journeyDate.setDate(journeyDate.getDate() + daysAhead);
  return journeyDate.toISOString().slice(0, 10);
}

export function getTestData() {
  const email = process.env.TRIPSTACK_EMAIL ?? 'rupert@tripstack.test';

  return {
    credentials: {
      email,
      password: process.env.TRIPSTACK_PASSWORD ?? 'Password@123',
    },
    journey: {
      from: 'BLR',
      to: 'HYD',
      date: getJourneyDate(),
    },
    passenger: {
      firstName: 'Lakhan',
      lastName: '1018',
      age: '18',
      gender: 'male',
      email,
      phone: '9876543210',
    },
    payment: {
      cardName: 'Lakhan 1018',
      cardNumber: '4111111111111111',
      expiry: '12/30',
      cvv: '123',
    },
    empId: '1018',
  };
}
