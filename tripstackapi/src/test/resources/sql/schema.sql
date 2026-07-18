DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    emp_id VARCHAR(10) PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    display_name VARCHAR(100) NOT NULL,
    role VARCHAR(30) NOT NULL
);

CREATE TABLE bookings (
    id VARCHAR(50) PRIMARY KEY,
    pnr VARCHAR(30) NOT NULL UNIQUE,
    emp_id VARCHAR(10) NOT NULL,
    inventory_id VARCHAR(100) NOT NULL,
    journey_type VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    amount_paise BIGINT NOT NULL,
    refundable BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_booking_user
        FOREIGN KEY (emp_id)
        REFERENCES users(emp_id)
);

CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id VARCHAR(50) NOT NULL,
    amount_paise BIGINT NOT NULL,
    payment_status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_payment_booking
        FOREIGN KEY (booking_id)
        REFERENCES bookings(id)
);

INSERT INTO users (emp_id, email, display_name, role)
VALUES
('1001', 'admin@tripstack.test', 'Admin User', 'admin'),
('1002', 'viewer@tripstack.test', 'Viewer User', 'viewer'),
('1017', 'quinn@tripstack.test', 'Quinn', 'traveller');

INSERT INTO bookings
(
    id,
    pnr,
    emp_id,
    inventory_id,
    journey_type,
    status,
    amount_paise,
    refundable
)
VALUES
(
    'BK-1017-0001',
    'TS-1017-0001',
    '1017',
    'FL-BOMGOI-001',
    'flight',
    'CONFIRMED',
    845000,
    TRUE
);

INSERT INTO payments
(
    booking_id,
    amount_paise,
    payment_status
)
VALUES
(
    'BK-1017-0001',
    845000,
    'SUCCESS'
);