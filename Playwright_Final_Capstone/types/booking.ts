export type Credentials = {
  email: string;
  password: string;
};

export type PassengerDetails = {
  firstName: string;
  lastName: string;
  age: string;
  email: string;
  phone: string;
};

export type CardDetails = {
  name: string;
  number: string;
  expiry: string;
  cvv: string;
};

export type FlightSearchCriteria = {
  origin: string;
  destination: string;
  cabinClass: string;
  daysToTravel: number;
};

export type BookingData = FlightSearchCriteria & {
  flightName: string;
  seat?: string;
  seatPosition?: string;
  employeeId: string;
};
