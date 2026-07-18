CREATE TABLE bookings
(
    pnr             VARCHAR(30) PRIMARY KEY,
    inventory_id    VARCHAR(50) NOT NULL,
    journey_type    VARCHAR(20) NOT NULL,
    status          VARCHAR(20) NOT NULL,
    emp_id          VARCHAR(20) NOT NULL,
    refundable      BOOLEAN NOT NULL,
    seat_id         VARCHAR(20) NOT NULL
);