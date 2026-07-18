CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT NOT NULL AUTO_INCREMENT,
    pnr VARCHAR(30) NOT NULL,
    source_city VARCHAR(50),
    destination_city VARCHAR(50),
    journey_date DATE,
    total_amount DECIMAL(10,2),
    booking_status VARCHAR(30),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (booking_id),
    UNIQUE KEY uk_bookings_pnr (pnr)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS passengers (
    passenger_id INT NOT NULL AUTO_INCREMENT,
    booking_id INT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    age INT,
    gender VARCHAR(10),
    PRIMARY KEY (passenger_id),
    KEY idx_passengers_booking_id (booking_id),
    CONSTRAINT fk_passengers_booking
        FOREIGN KEY (booking_id)
        REFERENCES bookings (booking_id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS payments (
    payment_id INT NOT NULL AUTO_INCREMENT,
    booking_id INT,
    payment_method VARCHAR(30),
    payment_status VARCHAR(30),
    amount DECIMAL(10,2),
    PRIMARY KEY (payment_id),
    KEY idx_payments_booking_id (booking_id),
    CONSTRAINT fk_payments_booking
        FOREIGN KEY (booking_id)
        REFERENCES bookings (booking_id)
        ON DELETE CASCADE
) ENGINE=InnoDB;