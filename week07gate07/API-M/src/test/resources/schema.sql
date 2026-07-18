DROP TABLE IF EXISTS booking_seats;
DROP TABLE IF EXISTS bookings;

CREATE TABLE bookings (

    booking_uuid      VARCHAR(100) PRIMARY KEY,

    pnr               VARCHAR(30),

    emp_id            VARCHAR(10) NOT NULL,

    journey_type      VARCHAR(20),

    inventory_id      VARCHAR(60),

    booking_state     VARCHAR(30),

    amount_paise      INT,

    refundable        BOOLEAN,

    hold_expires_at   TIMESTAMP,

    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);

CREATE TABLE booking_seats (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    booking_uuid VARCHAR(100),

    seat_id VARCHAR(10),

    CONSTRAINT fk_booking
        FOREIGN KEY (booking_uuid)
        REFERENCES bookings(booking_uuid)
        ON DELETE CASCADE

);